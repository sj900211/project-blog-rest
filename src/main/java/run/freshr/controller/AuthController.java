package run.freshr.controller;

import static run.freshr.common.config.URIConfig.uriAuthCrypto;
import static run.freshr.common.config.URIConfig.uriAuthInfo;
import static run.freshr.common.config.URIConfig.uriAuthPassword;
import static run.freshr.common.config.URIConfig.uriAuthSignIn;
import static run.freshr.common.config.URIConfig.uriAuthSignOut;
import static run.freshr.common.config.URIConfig.uriAuthToken;
import static run.freshr.domain.auth.enumeration.Role.Secured.MANAGER_MAJOR;
import static run.freshr.domain.auth.enumeration.Role.Secured.MANAGER_MINOR;
import static run.freshr.domain.auth.enumeration.Role.Secured.STAFF_MAJOR;
import static run.freshr.domain.auth.enumeration.Role.Secured.STAFF_MINOR;
import static run.freshr.domain.auth.enumeration.Role.Secured.USER;
import static run.freshr.domain.auth.enumeration.Role.Secured.ANONYMOUS;

import jakarta.validation.Valid;
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

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, STAFF_MAJOR, STAFF_MINOR, USER, ANONYMOUS})
  @GetMapping(uriAuthCrypto)
  public ResponseEntity<?> getPublicKey() {
    log.info("AuthController.getPublicKey");

    return service.getPublicKey();
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, STAFF_MAJOR, STAFF_MINOR, USER, ANONYMOUS})
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

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, STAFF_MAJOR, STAFF_MINOR, USER})
  @PostMapping(uriAuthSignOut)
  public ResponseEntity<?> signOut() {
    log.info("AuthController.signOut");

    return service.signOut();
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, STAFF_MAJOR, STAFF_MINOR, USER})
  @GetMapping(uriAuthInfo)
  public ResponseEntity<?> getInfo() {
    log.info("AuthController.getInfo");

    return service.getInfo();
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, STAFF_MAJOR, STAFF_MINOR, USER})
  @PutMapping(uriAuthPassword)
  public ResponseEntity<?> updatePassword(@RequestBody @Valid SignChangePasswordRequest dto) {
    log.info("AuthController.updatePassword");

    return service.updatePassword(dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, STAFF_MAJOR, STAFF_MINOR, USER})
  @PutMapping(uriAuthInfo)
  public ResponseEntity<?> updateInfo(@RequestBody @Valid SignUpdateRequest dto) {
    log.info("AuthController.updateInfo");

    return service.updateInfo(dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, STAFF_MAJOR, STAFF_MINOR, USER})
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
