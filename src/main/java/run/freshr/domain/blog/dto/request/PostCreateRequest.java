package run.freshr.domain.blog.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.mapping.dto.request.PostAttachMappingSaveRequest;
import run.freshr.domain.mapping.dto.request.PostHashtagMappingSaveRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

  @NotEmpty
  private String title;

  @NotEmpty
  private String contents;

  @NotNull
  private Boolean useFlag;

  private List<PostHashtagMappingSaveRequest> hashtagList;

  private List<PostAttachMappingSaveRequest> attachList;

}
