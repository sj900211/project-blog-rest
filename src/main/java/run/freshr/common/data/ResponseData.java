package run.freshr.common.data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * 공통 Response data.
 *
 * @author FreshR
 * @apiNote 모든 API 의 반환 구조를 통일하기위한 모델
 * @since 2023. 1. 12. 오후 6:03:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class ResponseData {

  /**
   * Name
   *
   * @apiNote 에러가 발생한 경우 에러의 이름. 중요하지 않은 정보.
   * @since 2023. 1. 12. 오후 6:03:56
   */
  private String name;
  /**
   * Code
   *
   * @apiNote 에러가 발생한 경우 Status 외에 에러 상태를 알 수 있는 코드<br>
   * 대부분의 경우 사용하지 않을 정보지만<br>
   * 같은 Status 의 에러에서 분기처리가 필요한 경우 값을 다르게 설정해서 반환
   * @since 2023. 1. 12. 오후 6:03:56
   */
  private String code;
  /**
   * Message
   *
   * @apiNote 처리 메시지
   * @since 2023. 1. 12. 오후 6:03:56
   */
  private String message;
  /**
   * Page
   *
   * @apiNote 반환 데이터가 Page 데이터일 경우 사용되는 필드
   * @since 2023. 1. 12. 오후 6:03:56
   */
  private Page<?> page;
  /**
   * List
   *
   * @apiNote 반환 데이터가 List 데이터일 경우 사용되는 필드
   * @since 2023. 1. 12. 오후 6:03:56
   */
  private Collection<?> list;
  /**
   * Data
   *
   * @apiNote 반환 데이터가 Page 와 List 가 아닌 모든 데이터일 경우 사용되는 필드
   * @since 2023. 1. 12. 오후 6:03:56
   */
  private Object data;

}
