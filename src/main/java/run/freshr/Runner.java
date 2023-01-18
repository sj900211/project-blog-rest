package run.freshr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Runner.
 *
 * @author FreshR
 * @apiNote Application Run 마지막에 동작하는 Class
 * @since 2022. 12. 23. 오후 2:15:05
 */
@Slf4j
@Component
public class Runner implements ApplicationRunner {

  /**
   * Application Run 마지막에 동작하는 Method
   *
   * @param args the args
   * @apiNote 지금 사용되는 기능은 없음. 그저 멋...⭐
   * @author FreshR
   * @since 2022. 12. 23. 오후 2:15:05
   */
  @Override
  public void run(ApplicationArguments args) {
    log.info("-------------------------------------------------------------------");
    log.info("_______ .______       _______     _______. __    __  .______");
    log.info("|   ____||   _  \\     |   ____|   /       ||  |  |  | |   _  \\");
    log.info("|  |__   |  |_)  |    |  |__     |   (----`|  |__|  | |  |_)  |");
    log.info("|   __|  |      /     |   __|     \\   \\    |   __   | |      /");
    log.info("|  |     |  |\\  \\----.|  |____.----)   |   |  |  |  | |  |\\  \\----.");
    log.info("|__|     | _| `._____||_______|_______/    |__|  |__| | _| `._____|");
    log.info("-------------------------------------------------------------------");
  }

}
