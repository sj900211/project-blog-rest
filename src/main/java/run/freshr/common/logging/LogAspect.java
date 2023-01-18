package run.freshr.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;
import static run.freshr.common.security.TokenProvider.signedId;
import static run.freshr.common.security.TokenProvider.signedRole;
import static run.freshr.domain.auth.enumeration.Role.ROLE_ANONYMOUS;

/**
 * 공통 Logging 설정.
 *
 * @author FreshR
 * @apiNote 공통 Logging 설정
 * @since 2023. 1. 12. 오후 6:51:17
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

  /**
   * controller package 공통 Logging 설정.
   *
   * @param proceedingJoinPoint proceeding join point
   * @return object
   * @throws Throwable throwable
   * @apiNote api note
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:51:17
   */
  @Around("execution(* run.freshr.controller..*.*(..))")
  public Object controllerLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    log.info("**** LOG START");
    log.info(
        "**** " + proceedingJoinPoint.getSignature().getDeclaringTypeName()
            + "." + proceedingJoinPoint.getSignature().getName()
    );
    log.info("**** Role: " + ofNullable(signedRole.get()).orElse(ROLE_ANONYMOUS).getKey());
    log.info("**** Id: " + ofNullable(signedId.get()).orElse(0L));

    Object result = proceedingJoinPoint.proceed();

    log.info("**** LOG FINISH");

    return result;
  }

}
