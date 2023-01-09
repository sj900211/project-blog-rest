package run.freshr.service;

import static run.freshr.common.utils.RestUtil.error;
import static run.freshr.common.utils.RestUtil.getConfig;
import static run.freshr.common.utils.RestUtil.getExceptions;
import static run.freshr.common.utils.RestUtil.getSignedAccount;
import static run.freshr.common.utils.RestUtil.getSignedId;
import static run.freshr.common.utils.RestUtil.getSignedManager;
import static run.freshr.common.utils.RestUtil.getSignedRole;
import static run.freshr.common.utils.RestUtil.getSignedStaff;
import static run.freshr.common.utils.RestUtil.ok;
import static run.freshr.domain.auth.enumeration.Role.ROLE_ALPHA;
import static run.freshr.domain.auth.enumeration.Role.ROLE_BETA;
import static run.freshr.domain.auth.enumeration.Role.ROLE_DELTA;
import static run.freshr.domain.auth.enumeration.Role.ROLE_GAMMA;
import static run.freshr.domain.auth.enumeration.Role.ROLE_USER;
import static run.freshr.utils.CryptoUtil.decryptRsa;
import static run.freshr.utils.CryptoUtil.encryptRsa;
import static run.freshr.utils.MapperUtil.map;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.data.ResponseData;
import run.freshr.common.data.ResponseData.ResponseDataBuilder;
import run.freshr.common.dto.response.KeyResponse;
import run.freshr.common.security.JwtTokenProvider;
import run.freshr.domain.auth.dto.request.EncryptRequest;
import run.freshr.domain.auth.dto.request.RefreshTokenRequest;
import run.freshr.domain.auth.dto.request.SignChangePasswordRequest;
import run.freshr.domain.auth.dto.request.SignInRequest;
import run.freshr.domain.auth.dto.request.SignUpdateRequest;
import run.freshr.domain.auth.dto.response.AccountResponse;
import run.freshr.domain.auth.dto.response.EncryptResponse;
import run.freshr.domain.auth.dto.response.ManagerResponse;
import run.freshr.domain.auth.dto.response.RefreshTokenResponse;
import run.freshr.domain.auth.dto.response.SignInResponse;
import run.freshr.domain.auth.dto.response.StaffResponse;
import run.freshr.domain.auth.entity.Sign;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;
import run.freshr.domain.auth.redis.RsaPair;
import run.freshr.domain.auth.unit.AccessRedisUnit;
import run.freshr.domain.auth.unit.RefreshRedisUnit;
import run.freshr.domain.auth.unit.RsaPairUnit;
import run.freshr.domain.auth.unit.SignUnit;
import run.freshr.utils.CryptoUtil;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

  private final SignUnit signUnit;

  private final AccessRedisUnit authAccessUnit;
  private final RefreshRedisUnit authRefreshUnit;
  private final RsaPairUnit rsaPairUnit;

  private final JwtTokenProvider provider;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public ResponseEntity<?> getPublicKey() {
    log.info("AuthService.getPublicKey");

    KeyPair keyPar = CryptoUtil.getKeyPar();
    PublicKey publicKey = keyPar.getPublic();
    PrivateKey privateKey = keyPar.getPrivate();
    String encodePublicKey = CryptoUtil.encodePublicKey(publicKey);
    String encodePrivateKey = CryptoUtil.encodePrivateKey(privateKey);

    rsaPairUnit.save(RsaPair.createRedis(encodePublicKey, encodePrivateKey, LocalDateTime.now()));

    return ok(KeyResponse.<String>builder().key(encodePublicKey).build());
  }

  @Override
  public ResponseEntity<?> getEncryptRsa(EncryptRequest dto) {
    log.info("AuthService.getEncryptRsa");

    String encrypt = encryptRsa(dto.getPlain(), dto.getRsa());

    return ok(EncryptResponse.builder().encrypt(encrypt).build());
  }

  @Override
  @Transactional
  public ResponseEntity<?> signIn(SignInRequest dto) {
    log.info("AuthService.signIn");

    String encodePublicKey = dto.getRsa();

    if (!rsaPairUnit.checkRsa(encodePublicKey)) {
      return error(getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getPrivateKey();
    String username = decryptRsa(dto.getUsername(), encodePrivateKey);

    if (!signUnit.exists(username)) {
      return error(getExceptions().getEntityNotFound());
    }

    Sign entity = signUnit.get(username);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getUnAuthenticated());
    }

    if (!passwordEncoder
        .matches(decryptRsa(dto.getPassword(), encodePrivateKey), entity.getPassword())) {
      return error(getExceptions().getUnAuthenticated());
    }

    entity.signed();

    Long id = entity.getId();

    // 토큰 발급
    String accessToken = provider.generateAccessToken(id);
    String refreshToken = provider.generateRefreshToken(id);

    // 토큰 등록
    authAccessUnit.save(AccessRedis.createRedis(accessToken, id, entity.getPrivilege().getRole()));
    authRefreshUnit.save(RefreshRedis.createRedis(refreshToken, authAccessUnit.get(accessToken)));

    SignInResponse response = SignInResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();

    return ok(response);
  }

  @Override
  @Transactional
  public ResponseEntity<?> signOut(HttpServletRequest request) {
    log.info("AuthService.signOut");

    String token = provider.resolve(request);

    authRefreshUnit.delete(authAccessUnit.get(token));
    authAccessUnit.delete(token);

    return ok();
  }

  @Override
  public ResponseEntity<?> getInfo() {
    log.info("AuthService.getInfo");

    ResponseDataBuilder builder = ResponseData.builder();
    Role signedRole = getSignedRole();

    if (signedRole.equals(ROLE_USER)) {
      builder.data(map(getSignedAccount(), AccountResponse.class));
    }

    if (!(!signedRole.equals(ROLE_DELTA) && !signedRole.equals(ROLE_GAMMA))) {
      builder.data(map(getSignedStaff(), StaffResponse.class));
    }

    if (!(!signedRole.equals(ROLE_BETA) && !signedRole.equals(ROLE_ALPHA))) {
      builder.data(map(getSignedManager(), ManagerResponse.class));
    }

    return ok(builder.build());
  }

  @Override
  @Transactional
  public ResponseEntity<?> updatePassword(SignChangePasswordRequest dto) {
    log.info("AuthService.updatePassword");

    String encodePublicKey = dto.getRsa();

    if (!rsaPairUnit.checkRsa(encodePublicKey)) {
      return error(getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getPrivateKey();

    Sign entity = signUnit.get(getSignedId());

    if (!passwordEncoder
        .matches(decryptRsa(dto.getOriginPassword(), encodePrivateKey), entity.getPassword())) {
      return error(getExceptions().getUnAuthenticated());
    }

    entity.updatePassword(passwordEncoder.encode(decryptRsa(dto.getPassword(), encodePrivateKey)));

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> updateInfo(SignUpdateRequest dto) {
    log.info("AuthService.updateInfo");

    String encodePublicKey = dto.getRsa();

    if (!rsaPairUnit.checkRsa(encodePublicKey)) {
      return error(getExceptions().getAccessDenied());
    }

    Role signedRole = getSignedRole();
    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getPrivateKey();

    if (signedRole.equals(ROLE_USER)) {
      getSignedAccount().updateEntity(decryptRsa(dto.getName(), encodePrivateKey));
    }

    if (!(!signedRole.equals(ROLE_DELTA) && !signedRole.equals(ROLE_GAMMA))) {
      getSignedStaff().updateEntity(decryptRsa(dto.getName(), encodePrivateKey));
    }

    if (!(!signedRole.equals(ROLE_BETA) && !signedRole.equals(ROLE_ALPHA))) {
      getSignedManager().updateEntity(decryptRsa(dto.getName(), encodePrivateKey));
    }

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> removeInfo() {
    log.info("AuthService.removeInfo");

    Role signedRole = getSignedRole();
    Long id = getSignedId();

    if (signedRole.equals(ROLE_USER)) {
      getSignedAccount().removeEntity();
    }

    if (!(!signedRole.equals(ROLE_DELTA) && !signedRole.equals(ROLE_GAMMA))) {
      getSignedStaff().removeEntity();
    }

    if (!(!signedRole.equals(ROLE_BETA) && !signedRole.equals(ROLE_ALPHA))) {
      getSignedManager().removeEntity();
    }

    authRefreshUnit.delete(authAccessUnit.get(id));
    authAccessUnit.delete(id);

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> refreshAccessToken(RefreshTokenRequest dto) {
    log.info("AuthService.refreshToken");

    String refreshToken = dto.getRefreshToken();

    provider.validateRefresh(refreshToken);

    RefreshRedis refresh = authRefreshUnit.get(refreshToken); // Refresh Token 상세 조회
    LocalDateTime updateAt = refresh.getUpdateAt(); // Access Token 갱신 날짜 시간 조회
    AccessRedis access = authAccessUnit.get(refresh.getAccess().getId()); // Access Token 상세 조회
    String accessToken = access.getId(); // Access Token 조회
    Long id = access.getSignId(); // 계정 일련 번호 조회
    Role role = access.getRole(); // 계정 권한 조회

    // Access Token 이 발급된지 설정 시간을 넘었는지 확인. 넘었다면 로그아웃 처리
    long limit = getConfig().getAuthLimit(); // 60 일

    if (Duration.between(updateAt, LocalDateTime.now()).getSeconds() > limit) {
      authAccessUnit.delete(accessToken);
      authRefreshUnit.delete(refreshToken);

      return error(getExceptions().getUnAuthenticated());
    }

    // 새로운 Access Token 발급
    String newAccessToken = provider.generateAccessToken(id);

    authAccessUnit.delete(accessToken);
    authAccessUnit.save(AccessRedis.createRedis(newAccessToken, id, role));

    refresh.updateRedis(authAccessUnit.get(newAccessToken));
    authRefreshUnit.save(refresh);

    // 계정 최근 접속 날짜 시간 갱신
    signUnit.get(id).signed();

    RefreshTokenResponse response = RefreshTokenResponse
        .builder()
        .accessToken(newAccessToken)
        .build();

    return ok(response);
  }

}
