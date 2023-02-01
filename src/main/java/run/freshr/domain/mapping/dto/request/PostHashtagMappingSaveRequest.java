package run.freshr.domain.mapping.dto.request;

import jakarta.validation.constraints.NotEmpty;
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
public class PostHashtagMappingSaveRequest {

  @NotEmpty
  private IdRequest<String> hashtag;

  @NotEmpty
  @Size(min = 1)
  private Integer sort;

}
