package run.freshr.common.advice;

import static run.freshr.common.utils.RestUtil.error;
import static run.freshr.common.utils.RestUtil.getExceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.SQLSyntaxErrorException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.freshr.exception.DuplicateException;
import run.freshr.exception.ParameterException;
import run.freshr.exception.UnAuthenticatedException;

/**
 * AdviceController.
 *
 * @author FreshR
 * @apiNote 예외처리 공통 설정
 * @since 2023. 1. 12. 오후 5:18:42
 */
@RestControllerAdvice
public class AdviceController {

  //   ______  __    __       _______.___________.  ______   .___  ___.
  //  /      ||  |  |  |     /       |           | /  __  \  |   \/   |
  // |  ,----'|  |  |  |    |   (----`---|  |----`|  |  |  | |  \  /  |
  // |  |     |  |  |  |     \   \       |  |     |  |  |  | |  |\/|  |
  // |  `----.|  `--'  | .----)   |      |  |     |  `--'  | |  |  |  |
  //  \______| \______/  |_______/       |__|      \______/  |__|  |__|

  /**
   * Parameter exception.
   *
   * @param e e
   * @return response entity
   * @apiNote Validate 에러 정의
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:42
   */
  @ExceptionHandler(value = {ParameterException.class})
  protected ResponseEntity<?> parameterException(ParameterException e) {
    return error(getExceptions().getParameter(), e.getMessage());
  }

  /**
   * Duplicate exception.
   *
   * @param e e
   * @return response entity
   * @apiNote 중복 에러
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {DuplicateException.class})
  protected ResponseEntity<?> duplicateException(DuplicateException e) {
    return error(getExceptions().getDuplicate(), e.getMessage());
  }

  /**
   * Un authenticated exception.
   *
   * @param e e
   * @return response entity
   * @apiNote 인증 인가 에러 정의
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {UnAuthenticatedException.class})
  protected ResponseEntity<?> unAuthenticatedException(UnAuthenticatedException e) {
    return error(getExceptions().getUnAuthenticated(), e.getMessage());
  }

  //  _______   _______  _______    ___      __    __   __      .___________.
  // |       \ |   ____||   ____|  /   \    |  |  |  | |  |     |           |
  // |  .--.  ||  |__   |  |__    /  ^  \   |  |  |  | |  |     `---|  |----`
  // |  |  |  ||   __|  |   __|  /  /_\  \  |  |  |  | |  |         |  |
  // |  '--'  ||  |____ |  |    /  _____  \ |  `--'  | |  `----.    |  |
  // |_______/ |_______||__|   /__/     \__\ \______/  |_______|    |__|

  /**
   * Null pointer exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {NullPointerException.class})
  protected ResponseEntity<?> nullPointerException(NullPointerException e) {
    return error(getExceptions().getNullPointer(), e.getMessage());
  }

  /**
   * Entity not found exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {EntityNotFoundException.class})
  protected ResponseEntity<?> entityNotFoundException(EntityNotFoundException e) {
    return error(getExceptions().getEntityNotFound(), e.getMessage());
  }

  /**
   * Io exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {IOException.class})
  protected ResponseEntity<?> ioException(IOException e) {
    return error(getExceptions().getIo(), e.getMessage());
  }

  /**
   * File size limit exceeded exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {FileSizeLimitExceededException.class})
  protected ResponseEntity<?> fileSizeLimitExceededException(
      FileSizeLimitExceededException e) {
    return error(getExceptions().getFileSizeLimitExceeded(), e.getMessage());
  }

  /**
   * Json processing exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {JsonProcessingException.class})
  protected ResponseEntity<?> jsonProcessingException(JsonProcessingException e) {
    return error(getExceptions().getJsonProcessing(), e.getMessage());
  }

  /**
   * Sql syntax error exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {SQLSyntaxErrorException.class})
  protected ResponseEntity<?> sqlSyntaxErrorException(SQLSyntaxErrorException e) {
    return error(getExceptions().getSqlSyntaxError(), e.getMessage());
  }

  /**
   * Invalid data access resource usage exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {InvalidDataAccessResourceUsageException.class})
  protected ResponseEntity<?> invalidDataAccessResourceUsageException(
      InvalidDataAccessResourceUsageException e) {
    if (e.getCause() instanceof SQLGrammarException) {
      if (e.getCause().getCause() instanceof SQLSyntaxErrorException) {
        return error(getExceptions().getSqlGrammar(), e.getCause().getCause().getMessage());
      } else {
        return error(getExceptions().getSqlSyntaxError(), e.getCause().getMessage());
      }
    }

    return error(getExceptions().getInvalidDataAccessResourceUsage(), e.getMessage());
  }

  /**
   * Data integrity violation exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {DataIntegrityViolationException.class})
  protected ResponseEntity<?> dataIntegrityViolationException(
      DataIntegrityViolationException e) {
    if (e.getCause() instanceof ConstraintViolationException) {
      if (e.getCause().getCause() instanceof BatchUpdateException) {
        return error(getExceptions().getBatchUpdate(), e.getCause().getCause().getMessage());
      } else {
        return error(getExceptions().getConstraintViolation(), e.getCause().getMessage());
      }
    } else if (e.getCause() instanceof DataException) {
      if (e.getCause().getCause() instanceof BatchUpdateException) {
        return error(getExceptions().getBatchUpdate(), e.getCause().getCause().getMessage());
      } else {
        return error(getExceptions().getData(), e.getCause().getMessage());
      }
    }

    return error(getExceptions().getDataIntegrityViolation(), e.getMessage());
  }

  /**
   * Batch update exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {BatchUpdateException.class})
  protected ResponseEntity<?> batchUpdateException(BatchUpdateException e) {
    return error(getExceptions().getBatchUpdate(), e.getMessage());
  }

  /**
   * Sql grammar exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {SQLGrammarException.class})
  protected ResponseEntity<?> sqlGrammarException(SQLGrammarException e) {
    return error(getExceptions().getSqlGrammar(), e.getMessage());
  }

  /**
   * Constraint violation exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {ConstraintViolationException.class})
  protected ResponseEntity<?> constraintViolationException(ConstraintViolationException e) {
    return error(getExceptions().getConstraintViolation(), e.getMessage());
  }

  /**
   * Data exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {DataException.class})
  protected ResponseEntity<?> dataException(DataException e) {
    return error(getExceptions().getData(), e.getMessage());
  }

  /**
   * Access denied exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {AccessDeniedException.class})
  protected ResponseEntity<?> accessDeniedException(AccessDeniedException e) {
    return error(getExceptions().getAccessDenied(), e.getMessage());
  }

  /**
   * Illegal state exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {IllegalStateException.class})
  protected ResponseEntity<?> illegalStateException(IllegalStateException e) {
    return error(getExceptions().getIllegalState(), e.getMessage());
  }

  /**
   * Illegal argument exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {IllegalArgumentException.class})
  protected ResponseEntity<?> illegalArgumentException(IllegalArgumentException e) {
    return error(getExceptions().getIllegalArgument(), e.getMessage());
  }

  /**
   * Expired jwt exception.
   *
   * @param e e
   * @return response entity
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:18:43
   */
  @ExceptionHandler(value = {ExpiredJwtException.class})
  protected ResponseEntity<?> expiredJwtException(ExpiredJwtException e) {
    return error(getExceptions().getExpiredJwt(), e.getMessage());
  }

}
