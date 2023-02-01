package run.freshr.domain.blog.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.domain.blog.enumeration.BlogViewType;
import run.freshr.domain.mapping.dto.request.BlogHashtagMappingSaveRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogCreateRequest {

  @NotEmpty
  private String title;

  private String description;

  @NotNull
  private BlogViewType viewType;

  private IdRequest<Long> cover;

  private List<BlogHashtagMappingSaveRequest> hashtagList;

}
