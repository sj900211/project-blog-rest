package run.freshr.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * Property 설정.
 *
 * @author FreshR
 * @apiNote 설정 파일에 작성한 property 를 읽어와서 Java 에서 사용할 수 있도록 설정
 * @since 2023. 1. 12. 오후 5:22:37
 */
@Configuration
@EnableConfigurationProperties
@Data
public class CustomConfig {

  /**
   * 헬스체크 마크업 파일의 Resource 데이터
   *
   * @apiNote 그저 멋을 위해...⭐
   * @since 2023. 1. 12. 오후 5:22:37
   */
  @Value("classpath:static/heartbeat/index.htm")
  private Resource heartbeat;

  /**
   * RSA 제한 시간 [초 단위]
   *
   * @apiNote RSA 를 발급받은 뒤 설정된 시간이 초과되면 사용할 수 없음
   * @since 2023. 1. 12. 오후 5:22:37
   */
  @Value("${freshr.rsa.limit}")
  private Integer rsaLimit;

  /**
   * 인증 인가 제한 시간 [초 단위]
   *
   * @apiNote 마지막 활동 시점으로부터 설정 시간이 초과되면 로그아웃 처리됨
   * @since 2023. 1. 12. 오후 5:22:37
   */
  @Value("${freshr.auth.limit}")
  private Long authLimit;

}
