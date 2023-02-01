package run.freshr.domain.community.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.community.enumeration.BoardNoticeExpose;
import run.freshr.domain.mapping.dto.request.BoardNoticeAttachMappingSaveRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardNoticeCreateRequest {

  @NotEmpty
  private String title;
  @NotEmpty
  private String contents;
  @NotNull
  private Boolean fixed;
  @NotNull
  private BoardNoticeExpose expose;
  private List<BoardNoticeAttachMappingSaveRequest> attachList;

}
