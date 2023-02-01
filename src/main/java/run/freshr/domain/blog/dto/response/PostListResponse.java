package run.freshr.domain.blog.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.domain.blog.extension.BlogResponseExtension;
import run.freshr.domain.mapping.dto.response.PostHashtagMappingForPostResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostListResponse extends BlogResponseExtension {

  private List<PostHashtagMappingForPostResponse> hashtagList;

}
