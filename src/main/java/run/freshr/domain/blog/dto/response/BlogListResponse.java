package run.freshr.domain.blog.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.domain.blog.enumeration.BlogViewType;
import run.freshr.domain.blog.extension.BlogResponseExtension;
import run.freshr.domain.common.dto.response.AttachResponse;
import run.freshr.domain.mapping.dto.response.BlogHashtagMappingForBlogResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlogListResponse extends BlogResponseExtension {

  private BlogViewType viewType;

  private String description;

  private AttachResponse cover;

  private List<BlogHashtagMappingForBlogResponse> hashtagList;

}
