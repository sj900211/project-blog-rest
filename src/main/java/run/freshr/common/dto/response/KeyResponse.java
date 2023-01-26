package run.freshr.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Key response.
 *
 * @param <ID> Key 데이터 유형
 * @author FreshR
 * @apiNote 반환 데이터에 key(String) 만 있는 경우 공통으로 사용하는 Response DTO<br> 자주 사용하는 구조기때문에 공통으로 생성
 * @since 2023. 1. 12. 오후 6:16:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyResponse<ID> {

  private ID key;

}
