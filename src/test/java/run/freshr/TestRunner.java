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

/**
 * Test runner.
 *
 * @author FreshR
 * @apiNote Application Run 마지막에 동작하는 Class<br>
 * Test 코드가 실행되기 전에 동작한다.
 * @since 2023. 1. 13. 오전 11:25:09
 */
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

  /**
   * Run.
   *
   * @param args args
   * @apiNote Test 코드는 크게 Given, When, Then 영역으로 나눌 수 있다.<br>
   * Given 영역은 테스트 데이터를 설정하는 부분인데<br>
   * Entity 구조가 복잡해질수록, 참조하는 데이터가 많아질수록<br>
   * Given 영역 코드가 점점 많아진다.<br>
   * 그리고 테스트마다 데이터를 설정하기 때문에 중복 코드도 많아진다.<br>
   * 그러다보니 관리 포인트가 점점 증가하게 되었다.<br>
   * 이 부분을 해결하고자 테스트 코드가 실행하기 전에 기본적인 데이터를 설정한다.<br>
   * Given 영역은 테스트에 따라 필요하면 생성을 할 수 있고<br>
   * 기본적으로는 각 Entity 에 페이징이 가능한 수의 데이터를 설정해서<br>
   * Given 영역이 대부분의 Test 코드에서 필요없게 되었다.
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:26:09
   */
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

  /**
   * 권한 설정.
   *
   * @apiNote 권한 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:33:50
   */
  private void setAuth() {
    alphaId = service.createManager(ALPHA, ALPHA.name().toLowerCase(), ALPHA.name());
    betaId = service.createManager(BETA, BETA.name().toLowerCase(), BETA.name());
    gammaId = service.createStaff(GAMMA, GAMMA.name().toLowerCase(), GAMMA.name());
    deltaId = service.createStaff(DELTA, DELTA.name().toLowerCase(), DELTA.name());
    userId = service.createAccount(USER.name().toLowerCase(), USER.name());
  }

  /**
   * 공통 설정.
   *
   * @apiNote 공통 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:34:15
   */
  private void setCommon() {
    attachId = service.createAttach("test.png", "temp", service.getSign(userId));
  }

}
