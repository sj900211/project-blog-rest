package run.freshr.domain.mapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.blog.dto.response.BlogSearchResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogHashtagMappingForHashtagResponse {

  private BlogSearchResponse blog;

}
