package run.freshr.domain.blog.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.mapping.dto.response.PostHashtagMappingForPostResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchResponse {

  private Long id;

  private String title;

  private LocalDateTime createAt;

  private List<PostHashtagMappingForPostResponse> hashtagList;

}
