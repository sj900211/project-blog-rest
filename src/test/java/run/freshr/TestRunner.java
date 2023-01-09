package run.freshr;

import static run.freshr.domain.auth.enumeration.Privilege.ALPHA;
import static run.freshr.domain.auth.enumeration.Privilege.BETA;
import static run.freshr.domain.auth.enumeration.Privilege.DELTA;
import static run.freshr.domain.auth.enumeration.Privilege.GAMMA;
import static run.freshr.domain.auth.enumeration.Privilege.USER;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import run.freshr.service.TestService;

@Slf4j
@Component
@Profile("test")
public class TestRunner implements ApplicationRunner {

  public static long alphaId;
  public static long betaId;
  public static long gammaId;
  public static long deltaId;
  public static long userId;
  public static long attachId;

  @Autowired
  private TestService service;

  @Override
  public void run(ApplicationArguments args) {
    log.debug(" _______       ___   .___________.    ___         .______       _______     _______. _______ .___________.");
    log.debug("|       \\     /   \\  |           |   /   \\        |   _  \\     |   ____|   /       ||   ____||           |");
    log.debug("|  .--.  |   /  ^  \\ `---|  |----`  /  ^  \\       |  |_)  |    |  |__     |   (----`|  |__   `---|  |----`");
    log.debug("|  |  |  |  /  /_\\  \\    |  |      /  /_\\  \\      |      /     |   __|     \\   \\    |   __|      |  |     ");
    log.debug("|  '--'  | /  _____  \\   |  |     /  _____  \\     |  |\\  \\----.|  |____.----)   |   |  |____     |  |     ");
    log.debug("|_______/ /__/     \\__\\  |__|    /__/     \\__\\    | _| `._____||_______|_______/    |_______|    |__|     ");

    setAuth();
    setCommon();
  }

  private void setAuth() {
    alphaId = service.createManager(ALPHA, ALPHA.name().toLowerCase(), ALPHA.name());
    betaId = service.createManager(BETA, BETA.name().toLowerCase(), BETA.name());
    gammaId = service.createStaff(GAMMA, GAMMA.name().toLowerCase(), GAMMA.name());
    deltaId = service.createStaff(DELTA, DELTA.name().toLowerCase(), DELTA.name());
    userId = service.createAccount(USER.name().toLowerCase(), USER.name());
  }

  private void setCommon() {
    attachId = service.createAttach("test.png", "temp", service.getSign(userId));
  }

}
