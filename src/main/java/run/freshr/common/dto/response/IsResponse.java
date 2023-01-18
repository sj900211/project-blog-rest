package run.freshr.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Is response.
 *
 * @author FreshR
 * @apiNote 반환 데이터에 is(Boolean) 만 있는 경우 공통으로 사용하는 Response DTO<br>
 * 자주 사용하는 구조기때문에 공통으로 생성
 * @since 2023. 1. 12. 오후 6:15:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsResponse {

  private Boolean is;

}
