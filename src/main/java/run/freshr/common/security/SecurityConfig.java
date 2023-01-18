package run.freshr.common.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static run.freshr.common.config.URIConfig.uriAuthToken;
import static run.freshr.common.config.URIConfig.uriCommonHeartbeat;
import static run.freshr.common.config.URIConfig.uriDocsAll;
import static run.freshr.common.config.URIConfig.uriFavicon;
import static run.freshr.common.config.URIConfig.uriH2All;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security 설정.
 *
 * @author FreshR
 * @apiNote Security 설정
 * @since 2023. 1. 12. 오후 7:02:35
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

  private final TokenProvider tokenProvider;

  /**
   * 비밀번호 암호화 방식 설정.
   *
   * @return password encoder
   * @apiNote 비밀번호 암호화 방식 설정
   * @author FreshR
   * @since 2023. 1. 12. 오후 7:02:35
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 권한 체크에서 제외할 항목 설정.
   *
   * @return web security customizer
   * @apiNote 권한 체크에서 제외할 항목 설정
   * @author FreshR
   * @since 2023. 1. 12. 오후 7:02:35
   */
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers(uriH2All, uriDocsAll, uriFavicon)
        .requestMatchers(GET, uriCommonHeartbeat)
        .requestMatchers(POST, uriAuthToken);
  }

  /**
   * Security 설정.
   *
   * @param httpSecurity http security
   * @return security filter chain
   * @throws Exception exception
   * @apiNote Security 설정
   * @author FreshR
   * @since 2023. 1. 12. 오후 7:02:35
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf().disable()
        .cors()
        .and()
        //세션 정책 설정
        .sessionManagement().sessionCreationPolicy(STATELESS)
        .and()
        .authorizeHttpRequests().anyRequest().permitAll()
        .and()
        .apply(new TokenSecurityConfig(tokenProvider));

    return httpSecurity.build();
  }

}
