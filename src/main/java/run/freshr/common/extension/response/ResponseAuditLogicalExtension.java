package run.freshr.common.extension.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.auth.dto.response.AuditResponse;

/**
 * 논리 삭제 정책을 가진 데이터의 공통 필드를 정의한 Response.
 *
 * @param <ID> ID 데이터 유형
 * @author FreshR
 * @apiNote 논리 삭제 정책을 가진 데이터의 공통 필드를 정의한 Response
 * @since 2023. 1. 12. 오후 6:27:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAuditLogicalExtension<ID> {

  protected ID id;

  protected AuditResponse creator;

  protected LocalDateTime createAt;

  protected AuditResponse updater;

  protected LocalDateTime updateAt;

}
