package run.freshr.domain.mapping.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.dto.request.IdRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostAttachMappingSaveRequest {

  @NotNull
  private IdRequest<Long> attach;

  @NotNull
  @Size(min = 1)
  private Integer sort;

}
