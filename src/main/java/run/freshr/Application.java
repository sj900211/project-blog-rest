package run.freshr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * Application.
 *
 * @author FreshR
 * @since 2022. 12. 23. 오후 2:13:44
 */
@SpringBootApplication
@EnableJpaAuditing
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
