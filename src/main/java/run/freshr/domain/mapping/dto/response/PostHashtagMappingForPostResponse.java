package run.freshr.domain.mapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.common.dto.response.HashtagResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostHashtagMappingForPostResponse {

  private HashtagResponse hashtag;

  private Integer sort;

}
