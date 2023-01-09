package run.freshr.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import run.freshr.domain.auth.dto.request.EncryptRequest;
import run.freshr.domain.auth.dto.request.RefreshTokenRequest;
import run.freshr.domain.auth.dto.request.SignChangePasswordRequest;
import run.freshr.domain.auth.dto.request.SignInRequest;
import run.freshr.domain.auth.dto.request.SignUpdateRequest;

public interface AuthService {

  ResponseEntity<?> getPublicKey();

  ResponseEntity<?> getEncryptRsa(EncryptRequest dto);

  ResponseEntity<?> signIn(SignInRequest dto);

  ResponseEntity<?> signOut(HttpServletRequest request);

  ResponseEntity<?> getInfo();

  ResponseEntity<?> updatePassword(SignChangePasswordRequest dto);

  ResponseEntity<?> updateInfo(SignUpdateRequest dto);

  ResponseEntity<?> removeInfo();

  ResponseEntity<?> refreshAccessToken(RefreshTokenRequest dto);

}
