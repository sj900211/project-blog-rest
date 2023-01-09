package run.freshr.service;

import static run.freshr.common.util.ThreadUtil.threadAccess;
import static run.freshr.common.util.ThreadUtil.threadPublicKey;
import static run.freshr.common.util.ThreadUtil.threadRefresh;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import run.freshr.common.security.JwtTokenProvider;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.entity.Manager;
import run.freshr.domain.auth.entity.Sign;
import run.freshr.domain.auth.entity.Staff;
import run.freshr.domain.auth.enumeration.Privilege;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;
import run.freshr.domain.auth.redis.RsaPair;
import run.freshr.domain.auth.unit.AccessRedisUnit;
import run.freshr.domain.auth.unit.AccountUnit;
import run.freshr.domain.auth.unit.ManagerUnit;
import run.freshr.domain.auth.unit.RefreshRedisUnit;
import run.freshr.domain.auth.unit.RsaPairUnit;
import run.freshr.domain.auth.unit.SignUnit;
import run.freshr.domain.auth.unit.StaffUnit;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.unit.AttachUnit;
import run.freshr.mappers.EnumGetter;
import run.freshr.mappers.EnumMapper;
import run.freshr.utils.CryptoUtil;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider provider;

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|
  private final EnumMapper enumMapper;

  @Override
  public Map<String, List<EnumGetter>> getEnumAll() {
    return enumMapper.getAll();
  }

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  private final AttachUnit attachUnit;

  @Override
  public long createAttach(String filename, String path, Sign creator) {
    return attachUnit.create(Attach.createEntity(
        "image/png",
        filename + ".png",
        path + "/" + filename + ".png",
        2048L,
        "alt",
        "title",
        creator
    ));
  }

  //      ___      __    __  .___________. __    __
  //     /   \    |  |  |  | |           ||  |  |  |
  //    /  ^  \   |  |  |  | `---|  |----`|  |__|  |
  //   /  /_\  \  |  |  |  |     |  |     |   __   |
  //  /  _____  \ |  `--'  |     |  |     |  |  |  |
  // /__/     \__\ \______/      |__|     |__|  |__|
  private final RsaPairUnit rsaPairUnit;
  private final AccessRedisUnit authAccessUnit;
  private final RefreshRedisUnit authRefreshUnit;
  private final SignUnit signUnit;
  private final ManagerUnit managerUnit;
  private final StaffUnit staffUnit;
  private final AccountUnit accountUnit;

  @Override
  public void createRsa() {
    KeyPair keyPar = CryptoUtil.getKeyPar();
    PublicKey publicKey = keyPar.getPublic();
    PrivateKey privateKey = keyPar.getPrivate();
    String encodePublicKey = CryptoUtil.encodePublicKey(publicKey);
    String encodePrivateKey = CryptoUtil.encodePrivateKey(privateKey);

    threadPublicKey.set(encodePublicKey);

    rsaPairUnit.save(RsaPair.createRedis(encodePublicKey, encodePrivateKey, LocalDateTime.now()));
  }

  @Override
  public void createAuth(Long id, Role role) {
    threadAccess.remove();
    threadRefresh.remove();

    // 토큰 발급
    String accessToken = provider.generateAccessToken(id);
    String refreshToken = provider.generateRefreshToken(id);

    threadAccess.set(accessToken);
    threadRefresh.set(refreshToken);

    // 토큰 등록
    authAccessUnit.save(AccessRedis.createRedis(accessToken, id, role));
    authRefreshUnit.save(RefreshRedis.createRedis(refreshToken, authAccessUnit.get(accessToken)));
  }

  @Override
  public void createAccess(String accessToken, Long id, Role role) {
    authAccessUnit.save(AccessRedis.createRedis(accessToken, id, role));
  }

  @Override
  public AccessRedis getAccess(String id) {
    return authAccessUnit.get(id);
  }

  @Override
  public void createRefresh(String refreshToken, String access) {
    authRefreshUnit.save(RefreshRedis.createRedis(refreshToken, getAccess(access)));
  }

  @Override
  public long createManager(Privilege privilege, String username, String name) {
    return managerUnit.create(Manager.createEntity(privilege,
        username, passwordEncoder.encode("1234"), name));
  }

  @Override
  public Manager getManager(long id) {
    return managerUnit.get(id);
  }

  @Override
  public long createStaff(Privilege privilege, String username, String name) {
    return staffUnit.create(Staff.createEntity(privilege,
        username, passwordEncoder.encode("1234"), name));
  }

  @Override
  public Staff getStaff(long id) {
    return staffUnit.get(id);
  }

  @Override
  public long createAccount(String username, String name) {
    return accountUnit.create(Account.createEntity(username,
        passwordEncoder.encode("1234"), name));
  }

  public Account getAccount(long id) {
    return accountUnit.get(id);
  }

  public Sign getSign(long id) {
    return signUnit.get(id);
  }

}
