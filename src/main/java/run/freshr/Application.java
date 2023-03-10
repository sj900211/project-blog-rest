package run.freshr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/**
 * Application.
 *
 * @author FreshR
 * @apiNote Application
 * @since 2022. 12. 23. 오후 2:13:44
 */
@SpringBootApplication
@EnableCaching
public class Application {

  /**
   * Entry point of application.
   *
   * @param args the input arguments
   * @apiNote main
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:16:29
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
