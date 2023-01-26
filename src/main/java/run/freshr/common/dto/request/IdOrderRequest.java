package run.freshr.common.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Id order request.
 *
 * @param <ID> ID 데이터 유형
 * @author FreshR
 * @apiNote 요청 데이터에 id 와 order 가 있는 경우 공통으로 사용하는 Request DTO<br> 자주 사용하는 구조기때문에 공통으로 생성
 * @since 2023. 1. 12. 오후 6:12:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdOrderRequest<ID> {

  @NotNull
  private ID id;

  @NotNull
  @Size(min = 1)
  private Integer order;

}
