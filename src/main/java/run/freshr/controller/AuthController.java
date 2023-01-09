package run.freshr.controller;

import static run.freshr.common.config.URIConfig.uriAuthCrypto;
import static run.freshr.common.config.URIConfig.uriAuthInfo;
import static run.freshr.common.config.URIConfig.uriAuthPassword;
import static run.freshr.common.config.URIConfig.uriAuthSignIn;
import static run.freshr.common.config.URIConfig.uriAuthSignOut;
import static run.freshr.common.config.URIConfig.uriAuthToken;
import static run.freshr.domain.auth.enumeration.Role.Secured.ALPHA;
import static run.freshr.domain.auth.enumeration.Role.Secured.ANONYMOUS;
import static run.freshr.domain.auth.enumeration.Role.Secured.BETA;
import static run.freshr.domain.auth.enumeration.Role.Secured.DELTA;
import static run.freshr.domain.auth.enumeration.Role.Secured.GAMMA;
import static run.freshr.domain.auth.enumeration.Role.Secured.USER;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.freshr.domain.auth.dto.request.EncryptRequest;
import run.freshr.domain.auth.dto.request.RefreshTokenRequest;
import run.freshr.domain.auth.dto.request.SignChangePasswordRequest;
import run.freshr.domain.auth.dto.request.SignInRequest;
import run.freshr.domain.auth.dto.request.SignUpdateRequest;
import run.freshr.service.AuthService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService service;

  @Secured({ALPHA, BETA, GAMMA, DELTA, USER, ANONYMOUS})
  @GetMapping(uriAuthCrypto)
  public ResponseEntity<?> getPublicKey() {
    log.info("AuthController.getPublicKey");

    return service.getPublicKey();
  }

  @Secured({ALPHA, BETA, GAMMA, DELTA, USER, ANONYMOUS})
  @PostMapping(uriAuthCrypto)
  public ResponseEntity<?> getEncryptRsa(@RequestBody @Valid EncryptRequest dto) {
    log.info("AuthController.getPublicKey");

    return service.getEncryptRsa(dto);
  }

  @Secured(ANONYMOUS)
  @PostMapping(uriAuthSignIn)
  public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest dto) {
    log.info("AuthController.signIn");

    return service.signIn(dto);
  }

  @Secured({ALPHA, BETA, GAMMA, DELTA, USER})
  @PostMapping(uriAuthSignOut)
  public ResponseEntity<?> signOut(HttpServletRequest request) {
    log.info("AuthController.signOut");

    return service.signOut(request);
  }

  @Secured({ALPHA, BETA, GAMMA, DELTA, USER})
  @GetMapping(uriAuthInfo)
  public ResponseEntity<?> getInfo() {
    log.info("AuthController.getInfo");

    return service.getInfo();
  }

  @Secured({ALPHA, BETA, GAMMA, DELTA, USER})
  @PutMapping(uriAuthPassword)
  public ResponseEntity<?> updatePassword(@RequestBody @Valid SignChangePasswordRequest dto) {
    log.info("AuthController.updatePassword");

    return service.updatePassword(dto);
  }

  @Secured({ALPHA, BETA, GAMMA, DELTA, USER})
  @PutMapping(uriAuthInfo)
  public ResponseEntity<?> updateInfo(@RequestBody @Valid SignUpdateRequest dto) {
    log.info("AuthController.updateInfo");

    return service.updateInfo(dto);
  }

  @Secured({ALPHA, BETA, GAMMA, DELTA, USER})
  @DeleteMapping(uriAuthInfo)
  public ResponseEntity<?> removeInfo() {
    log.info("AuthController.removeInfo");

    return service.removeInfo();
  }

  @PostMapping(uriAuthToken)
  public ResponseEntity<?> refreshAccessToken(@RequestBody @Valid RefreshTokenRequest dto) {
    log.info("AuthController.refreshAccessToken");

    return service.refreshAccessToken(dto);
  }

}
