package run.freshr.common.extension.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 물리 삭제 정책을 가진 데이터의 공통 필드를 정의한 Response.
 *
 * @param <ID> ID 데이터 유형
 * @author FreshR
 * @apiNote 물리 삭제 정책을 가진 데이터의 공통 필드를 정의한 Response
 * @since 2023. 1. 12. 오후 6:27:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePhysicalExtension<ID> {

  protected ID id;

  protected LocalDateTime createAt;

}
