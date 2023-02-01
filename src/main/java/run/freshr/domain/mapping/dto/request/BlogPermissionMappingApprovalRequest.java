package run.freshr.domain.mapping.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.dto.request.IdRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogPermissionMappingApprovalRequest {

  @NotNull
  private IdRequest<Long> account;

}
