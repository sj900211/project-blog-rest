package run.freshr.common.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

/**
 * H2 config.
 *
 * @author FreshR
 * @apiNote Test 환경에서 DB 툴에서 H2 에 접속하기 위한 설정
 * @since 2022. 12. 23. 오후 3:38:09
 */
@Profile("test")
@Configuration
public class H2Config {

  @Bean
  public Server h2TcpServer() throws SQLException {
    return Server.createTcpServer().start();
  }

}
