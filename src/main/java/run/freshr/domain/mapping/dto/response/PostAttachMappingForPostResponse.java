package run.freshr.domain.mapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.common.dto.response.AttachResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAttachMappingForPostResponse {

  private AttachResponse attach;
  private Integer sort;

}
