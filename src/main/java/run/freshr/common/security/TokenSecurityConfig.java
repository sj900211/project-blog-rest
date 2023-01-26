package run.freshr.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 토큰 권한 설정.
 *
 * @author FreshR
 * @apiNote 토큰 권한 설정
 * @since 2023. 1. 12. 오후 6:57:46
 */
@RequiredArgsConstructor
public class TokenSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final TokenProvider tokenProvider;

  /**
   * 권한 Filter 에 TokenProvider 주입.
   *
   * @param http http
   * @apiNote 권한 Filter 에 {@link TokenProvider} 주입
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:57:46
   */
  @Override
  public void configure(HttpSecurity http) {
    TokenAuthenticationFilter filter = new TokenAuthenticationFilter(tokenProvider);

    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
  }

}
