package run.freshr.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableConfigurationProperties
@Data
public class CustomConfig {

  @Value("classpath:static/heartbeat/index.htm")
  private Resource heartbeat;

  @Value("${freshr.rsa.limit}")
  private Integer rsaLimit;

  @Value("${freshr.auth.limit}")
  private Long authLimit;

}
