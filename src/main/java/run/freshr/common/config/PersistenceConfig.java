package run.freshr.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import run.freshr.domain.auth.entity.Sign;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
@EnableJpaRepositories("**.**.domain.**.repository.jpa")
public class PersistenceConfig {

  @Bean
  public AuditorAware<Sign> auditorProvider() {
    return new AuditorAwareImpl();
  }

}
