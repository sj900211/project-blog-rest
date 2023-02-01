package run.freshr.domain.blog.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.blog.enumeration.BlogViewType;
import run.freshr.domain.mapping.dto.response.BlogHashtagMappingForBlogResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogSearchResponse {

  private Long id;

  private BlogViewType viewType;

  private String title;

  private LocalDateTime createAt;

  private List<BlogHashtagMappingForBlogResponse> hashtagList;

}
