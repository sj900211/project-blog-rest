package run.freshr.domain.mapping.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.domain.blog.enumeration.BlogRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogPermissionMappingSaveRequest {

  @NotNull
  private IdRequest<Long> account;

  private BlogRole role;

}
