package run.freshr.domain.common.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.dto.request.IdRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachOrderRequest {

  @NotEmpty
  private IdRequest<Long> attach;

  @NotEmpty
  @Size(min = 1)
  private Integer order;

}
