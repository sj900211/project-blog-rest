package run.freshr.domain.common.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HashtagStatisticsResponse {

  private String id;

  private Long blogCount;

  private Long postCount;

  @QueryProjection
  public HashtagStatisticsResponse(String id, Long blogCount, Long postCount) {
    this.id = id;
    this.blogCount = blogCount;
    this.postCount = postCount;
  }

}
