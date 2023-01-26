package run.freshr.common.utils;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
import static java.text.MessageFormat.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static run.freshr.common.security.TokenProvider.signedId;
import static run.freshr.common.security.TokenProvider.signedRole;
import static run.freshr.domain.auth.enumeration.Role.ROLE_MANAGER_MAJOR;
import static run.freshr.domain.auth.enumeration.Role.ROLE_MANAGER_MINOR;
import static run.freshr.domain.auth.enumeration.Role.ROLE_USER;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import run.freshr.common.config.CustomConfig;
import run.freshr.common.data.ResponseData;
import run.freshr.common.dto.response.IdResponse;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.unit.AccountUnit;
import run.freshr.response.ExceptionsResponse;

/**
 * Rest util.
 *
 * @author FreshR
 * @apiNote 자주 사용하는 공통 기능을 정의
 * @since 2023. 1. 13. 오전 10:14:25
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RestUtil {

  private static final ObjectMapper objectMapper;

  /**
   * Environment
   *
   * @apiNote 실행중인 서비스의 환경에 접근 및 조회
   * @since 2023. 1. 13. 오전 10:14:27
   */
  private static Environment environment;
  private static CustomConfig customConfig;
  private static ExceptionsResponse exceptionsResponse;

  private static AccountUnit accountUnit;

  /**
   * DATE FORMAT
   *
   * @apiNote 기본 DATE 포맷
   * @since 2023. 1. 13. 오전 10:14:28
   */
  private static final String DATE_FORMAT = "yyyy-MM-dd";
  /**
   * DATE TIME FORMAT
   *
   * @apiNote 기본 DATETIME 포맷
   * @since 2023. 1. 13. 오전 10:14:28
   */
  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  static {
    objectMapper = new ObjectMapper();

    JavaTimeModule javaTimeModule = new JavaTimeModule();

    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(ofPattern(DATE_FORMAT)));
    javaTimeModule.addDeserializer(LocalDate.class,
        new LocalDateDeserializer(ofPattern(DATE_FORMAT)));
    javaTimeModule.addSerializer(LocalDateTime.class,
        new LocalDateTimeSerializer(ofPattern(DATE_TIME_FORMAT)));
    javaTimeModule.addDeserializer(LocalDateTime.class,
        new LocalDateTimeDeserializer(ofPattern(DATE_TIME_FORMAT)));

    objectMapper.registerModule(javaTimeModule);
  }

  /**
   * 생성자.
   *
   * @param environment        environment
   * @param customConfig       custom config
   * @param exceptionsResponse exceptions response
   * @param accountUnit        account unit
   * @apiNote 의존성 주입
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:25
   */
  @Autowired
  public RestUtil(Environment environment, CustomConfig customConfig,
      ExceptionsResponse exceptionsResponse, AccountUnit accountUnit) {
    RestUtil.environment = environment;
    RestUtil.customConfig = customConfig;
    RestUtil.exceptionsResponse = exceptionsResponse;
    RestUtil.accountUnit = accountUnit;
  }

  /**
   * View.
   *
   * @param modelAndView model and view
   * @param prefix       prefix
   * @param paths        paths
   * @return model and view
   * @apiNote ModelAndView 객체를 사용해서 View 클래스를 반환하는 기능
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ModelAndView view(final ModelAndView modelAndView, final String prefix,
      final String... paths) {
    log.info("RestUtil.view");

    StringBuilder uri = new StringBuilder(prefix);

    for (String path : paths) {
      uri.append("/").append(path);
    }

    modelAndView.setViewName(uri.toString());

    return modelAndView;
  }

  /**
   * 성공 반환.
   *
   * @return response entity
   * @apiNote 반환 데이터가 없는 구조(기본적으로 처리 메시지는 추가해서 반환된다.)
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ResponseEntity<?> ok() {
    log.info("RestUtil.ok");

    return ok(exceptionsResponse.getSuccess().getMessage());
  }

  /**
   * 성공 반환.
   *
   * @param message message
   * @return response entity
   * @apiNote 처리 메시지를 설정해서 반환
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ResponseEntity<?> ok(final String message) {
    log.info("RestUtil.ok");

    return ok(ResponseData
        .builder()
        .message(ofNullable(message).orElse(exceptionsResponse.getSuccess().getMessage()))
        .build());
  }

  /**
   * 성공 반환.
   *
   * @param <T>  type parameter
   * @param data data
   * @return response entity
   * @apiNote 객체를 설정해서 반환
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static <T> ResponseEntity<?> ok(final T data) {
    log.info("RestUtil.ok");

    return ok(ResponseData
        .builder()
        .message(exceptionsResponse.getSuccess().getMessage())
        .data(data)
        .build());
  }

  /**
   * 성공 반환.
   *
   * @param <T>  type parameter
   * @param list list
   * @return response entity
   * @apiNote List 를 설정해서 반환
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static <T> ResponseEntity<?> ok(final List<T> list) {
    log.info("RestUtil.ok");

    return ok(ResponseData
        .builder()
        .message(exceptionsResponse.getSuccess().getMessage())
        .list(list)
        .build());
  }

  /**
   * 성공 반환.
   *
   * @param <T>  type parameter
   * @param page page
   * @return response entity
   * @apiNote Page 를 설정해서 반환
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static <T> ResponseEntity<?> ok(final Page<T> page) {
    log.info("RestUtil.ok");

    return ok(ResponseData
        .builder()
        .message(exceptionsResponse.getSuccess().getMessage())
        .page(page)
        .build());
  }

  /**
   * 성공 반환.
   *
   * @param body body
   * @return response entity
   * @apiNote {@link ResponseData} 를 작성해서 반환
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ResponseEntity<?> ok(final ResponseData body) {
    log.info("RestUtil.ok");

    return ResponseEntity
        .ok()
        .body(objectMapper.valueToTree(body));
  }

  /**
   * 에러 반환.
   *
   * @param httpStatus http status
   * @param message    message
   * @return response entity
   * @apiNote Status 와 처리 메시지를 설정 후 반환
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ResponseEntity<?> error(final HttpStatus httpStatus, final String message) {
    log.info("RestUtil.error");

    return error(
        httpStatus,
        null,
        null,
        ofNullable(message).orElse(exceptionsResponse.getError().getMessage())
    );
  }

  /**
   * 에러 반환.
   *
   * @param message message
   * @param args    args
   * @return response entity
   * @apiNote 처리 메시지가 pattern 을 사용할 때 format 처리 후 반환
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ResponseEntity<?> error(final String message, final Object[] args) {
    log.info("RestUtil.error");

    return error(
        exceptionsResponse.getError().getHttpStatus(),
        exceptionsResponse.getError().getHttpStatus().name(),
        null,
        format(ofNullable(message).orElse(exceptionsResponse.getError().getMessage()), args)
    );
  }

  /**
   * 에러 반환.
   *
   * @param exceptions exceptions
   * @return response entity
   * @apiNote 참조한 Library 의 Exception 객체를 사용해서 설정 후 반환
   * @author FreshR
   * @see <a
   * href="https://nexus.freshr.run/#browse/browse:maven-releases:run/freshr/exceptions">FreshR
   * Exceptions</a>
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ResponseEntity<?> error(final ExceptionsResponse.Exceptions exceptions) {
    log.info("RestUtil.error");

    return error(exceptions, null, null);
  }

  /**
   * 에러 반환.
   *
   * @param exceptions exceptions
   * @param message    message
   * @return response entity
   * @apiNote 참조한 Library 의 Exception 객체를 사용해서 설정하고 처리 메시지는 따로 설정해서 반환
   * @author FreshR
   * @see <a
   * href="https://nexus.freshr.run/#browse/browse:maven-releases:run/freshr/exceptions">FreshR
   * Exceptions</a>
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ResponseEntity<?> error(final ExceptionsResponse.Exceptions exceptions,
      final String message) {
    log.info("RestUtil.error");

    return error(exceptions, message, null);
  }

  /**
   * 에러 반환.
   *
   * @param exceptions exceptions
   * @param message    message
   * @param args       args
   * @return response entity
   * @apiNote 참조한 Library 의 Exception 객체를 사용해서 설정하고<br> 처리 메시지가 pattern 을 사용할 때 format 처리 후 따로 설정해서
   * 반환
   * @author FreshR
   * @see <a
   * href="https://nexus.freshr.run/#browse/browse:maven-releases:run/freshr/exceptions">FreshR
   * Exceptions</a>
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ResponseEntity<?> error(final ExceptionsResponse.Exceptions exceptions,
      final String message, final Object[] args) {
    log.info("RestUtil.error");

    return error(
        exceptions.getHttpStatus(),
        UPPER_UNDERSCORE.to(LOWER_HYPHEN, exceptions.getHttpStatus().name()),
        exceptions.getCode(),
        format(ofNullable(message).orElse(exceptions.getMessage()), args)
    );
  }

  /**
   * 에러 반환.
   *
   * @param httpStatus http status
   * @param name       name
   * @param code       code
   * @param message    message
   * @return response entity
   * @apiNote Status, 에러의 이름, 코드, 처리 메시지를 설정해서 반환
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ResponseEntity<?> error(final HttpStatus httpStatus, final String name,
      final String code, final String message) {
    log.info("RestUtil.error");

    return ResponseEntity
        .status(httpStatus)
        .body(objectMapper.valueToTree(
            ResponseData
                .builder()
                .name(name)
                .code(code)
                .message(message)
                .build()
        ));
  }

  /**
   * 에러 반환.
   *
   * @param bindingResult binding result
   * @return response entity
   * @apiNote {@link BindingResult} 를 반환<br>
   * TODO: 해당 에러 구조는 공통 구조와 다르기 때문에 공통 구조로 반환할 수 있도록 수정 예정
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ResponseEntity<?> error(final BindingResult bindingResult) {
    log.info("RestUtil.error");

    return ResponseEntity
        .badRequest()
        .body(bindingResult);
  }

  /**
   * 에러 반환.
   *
   * @param errors errors
   * @return response entity
   * @apiNote {@link Errors} 를 반환<br>
   * TODO: 해당 에러 구조는 공통 구조와 다르기 때문에 공통 구조로 반환할 수 있도록 수정 예정
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static ResponseEntity<?> error(final Errors errors) {
    log.info("RestUtil.error");

    return ResponseEntity
        .badRequest()
        .body(errors);
  }

  /**
   * Validate 잘못된 데이터 처리.
   *
   * @param name          name
   * @param bindingResult binding result
   * @apiNote {@link BindingResult} 에 항목을 추가
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:26
   */
  public static void rejectWrong(final String name, BindingResult bindingResult) {
    log.info("RestUtil.rejectWrong");

    bindingResult.rejectValue(name, "wrong value");
  }

  /**
   * Validate 잘못된 데이터 처리.
   *
   * @param name          name
   * @param description   description
   * @param bindingResult binding result
   * @apiNote {@link BindingResult} 에 항목을 추가
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static void rejectWrong(final String name, final String description,
      BindingResult bindingResult) {
    log.info("RestUtil.rejectWrong");

    bindingResult.rejectValue(name, "wrong value", description);
  }

  /**
   * Validate 잘못된 데이터 처리.
   *
   * @param name   name
   * @param errors errors
   * @apiNote {@link Errors} 에 항목을 추가
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static void rejectWrong(final String name, Errors errors) {
    log.info("RestUtil.rejectWrong");

    errors.rejectValue(name, "wrong value");
  }

  /**
   * Validate 잘못된 데이터 처리.
   *
   * @param name        name
   * @param description description
   * @param errors      errors
   * @apiNote {@link Errors} 에 항목을 추가
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static void rejectWrong(final String name, final String description, Errors errors) {
    log.info("RestUtil.rejectWrong");

    errors.rejectValue(name, "wrong value", description);
  }

  /**
   * Validate 필수값 누락 처리.
   *
   * @param bindingResult binding result
   * @param names         names
   * @apiNote {@link BindingResult} 에 항목을 추가
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static void rejectRequired(BindingResult bindingResult, final String... names) {
    log.info("RestUtil.rejectRequired");

    stream(names).forEach(name -> rejectRequired(name, bindingResult));
  }

  /**
   * Validate 필수값 누락 처리.
   *
   * @param name          name
   * @param bindingResult binding result
   * @apiNote {@link BindingResult} 에 항목을 추가
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static void rejectRequired(final String name, BindingResult bindingResult) {
    log.info("RestUtil.rejectRequired");

    bindingResult.rejectValue(name, "required value");
  }

  /**
   * Validate 필수값 누락 처리.
   *
   * @param errors errors
   * @param names  names
   * @apiNote {@link Errors} 에 항목을 추가
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static void rejectRequired(Errors errors, final String... names) {
    log.info("RestUtil.rejectRequired");

    stream(names).forEach(name -> rejectRequired(name, errors));
  }

  /**
   * Validate 필수값 누락 처리.
   *
   * @param name   name
   * @param errors errors
   * @apiNote {@link Errors} 에 항목을 추가
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static void rejectRequired(final String name, Errors errors) {
    log.info("RestUtil.rejectRequired");

    errors.rejectValue(name, "required value");
  }

  /**
   * Validate 권한 없음에 대한 처리.
   *
   * @param bindingResult binding result
   * @apiNote {@link BindingResult} 에 항목을 추가
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static void rejectAuth(BindingResult bindingResult) {
    log.info("RestUtil.rejectAuth");

    bindingResult.rejectValue("UnAuthenticated", "permission denied");
  }

  /**
   * Validate 권한 없음에 대한 처리.
   *
   * @param errors errors
   * @apiNote {@link Errors} 에 항목을 추가
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static void rejectAuth(Errors errors) {
    log.info("RestUtil.rejectAuth");

    errors.rejectValue("UnAuthenticated", "permission denied");
  }

  public static ExceptionsResponse getExceptions() {
    log.info("RestUtil.getExceptions");

    return RestUtil.exceptionsResponse;
  }

  /**
   * Check profile.
   *
   * @param profile profile
   * @return boolean
   * @apiNote 실행중인 서비스의 Profile 을 체크
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static boolean checkProfile(final String profile) {
    log.info("RestUtil.checkProfile");

    return stream(environment.getActiveProfiles())
        .anyMatch(active -> active.equalsIgnoreCase(profile));
  }

  public static CustomConfig getConfig() {
    log.info("RestUtil.getConfig");

    return customConfig;
  }

  /**
   * ID 반환.
   *
   * @param <ID> type parameter
   * @param id   id
   * @return id response
   * @apiNote {@link IdResponse} 를 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static <ID> IdResponse<ID> buildId(ID id) {
    return IdResponse.<ID>builder().id(id).build();
  }

  /**
   * 계정 일련 번호 조회.
   *
   * @return signed id
   * @apiNote 통신중인 계정의 일련 번호 조회
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static Long getSignedId() {
    log.info("RestUtil.getSignedId");

    return signedId.get();
  }

  /**
   * 권한 조회.
   *
   * @return signed role
   * @apiNote 통신중인 계정의 권한 조회
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static Role getSignedRole() {
    log.info("RestUtil.getSignedRole");

    return signedRole.get();
  }

  /**
   * 권한 체크.
   *
   * @param role role
   * @return boolean
   * @apiNote 통신중인 계정의 권한 체크
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static boolean checkRole(Role role) {
    log.info("RestUtil.checkRole");

    return getSignedRole().check(role);
  }

  /**
   * Manager 권한 체크.
   *
   * @return boolean
   * @apiNote 통신중인 계정의 권한이 ROLE_MANAGER_* 인지 체크
   * @author FreshR
   * @since 2023. 1. 23. 오전 1:29:20
   */
  public static boolean checkManager() {
    log.info("RestUtil.checkManager");

    return getSignedRole().check(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR);
  }

  /**
   * User 권한 체크.
   *
   * @return boolean
   * @apiNote 통신중인 계정의 권한이 ROLE_USER 인지 체크
   * @author FreshR
   * @since 2023. 1. 23. 오전 1:29:20
   */
  public static boolean checkUser() {
    log.info("RestUtil.checkUser");

    return getSignedRole().check(ROLE_USER);
  }

  /**
   * 계정 정보 조회.
   *
   * @return signed
   * @apiNote 통신중인 계정의 정보 조회
   * @author FreshR
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static Account getSigned() {
    log.info("RestUtil.getSigned");

    return accountUnit.get(getSignedId());
  }

}
