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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .antMatchers(uriH2All, uriDocsAll, uriFavicon)
        .antMatchers(GET, uriCommonHeartbeat)
        .antMatchers(POST, uriAuthToken);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf().disable()
        .cors()
        .and()
        //세션 정책 설정
        .sessionManagement().sessionCreationPolicy(STATELESS)
        .and()
        .authorizeRequests().anyRequest().permitAll()
        .and()
        .apply(new JwtSecurityConfig(jwtTokenProvider));

    return httpSecurity.build();
  }

}
