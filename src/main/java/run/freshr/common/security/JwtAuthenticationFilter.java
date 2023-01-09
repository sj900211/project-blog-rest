package run.freshr.common.security;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static run.freshr.common.security.JwtTokenProvider.signedId;
import static run.freshr.common.security.JwtTokenProvider.signedRole;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private JwtTokenProvider provider;

  public JwtAuthenticationFilter(JwtTokenProvider provider) {
    this.provider = provider;
  }

  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) {
    log.debug("**** SECURITY FILTER START");

    try {
      String accessToken = provider.resolve(request);

      provider.validateAccess(accessToken);
      provider.setThreadLocal(accessToken);

      SecurityContextHolder.getContext().setAuthentication(provider.getAuthentication());

      log.debug("**** Role: " + signedRole.get().name());
      log.debug("**** Id: " + signedId.get());

      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException e) {
      log.error("**** ExpiredJwtException ****");
      log.error("**** error message : " + e.getMessage());
      log.error("**** stack trace : " + Arrays.toString(e.getStackTrace()));

      SecurityContextHolder.clearContext();

      try {
        response.getWriter().write(provider.expiredJwt());
      } catch (IOException ex) {
        log.error("**** ExpiredJwtException > IOException ****");
        log.error("**** error message : " + e.getMessage());
        log.error("**** stack trace : " + Arrays.toString(e.getStackTrace()));

        ex.printStackTrace();
      }

      e.printStackTrace();
    } catch (Exception e) {
      log.error("**** Exception ****");
      log.error("**** error message : " + e.getMessage());
      log.error("**** stack trace : " + Arrays.toString(e.getStackTrace()));

      SecurityContextHolder.clearContext();

      response.setStatus(INTERNAL_SERVER_ERROR.value());

      e.printStackTrace();
    } finally {
      log.debug("**** SECURITY FILTER FINISH");
    }
  }

}
