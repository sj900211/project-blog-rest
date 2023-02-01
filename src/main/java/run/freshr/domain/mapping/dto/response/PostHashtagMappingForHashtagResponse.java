package run.freshr.domain.mapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.blog.dto.response.PostSearchResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostHashtagMappingForHashtagResponse {

  private PostSearchResponse post;

}
