package run.freshr.domain.community.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.domain.community.enumeration.BoardNoticeExpose;
import run.freshr.domain.community.extension.BoardResponseExtension;
import run.freshr.domain.mapping.dto.response.BoardNoticeAttachMappingForBoardNoticeResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BoardNoticeResponse extends BoardResponseExtension {

  private String contents;
  private Integer views;
  private Boolean fixed;
  private BoardNoticeExpose expose;

  private List<BoardNoticeAttachMappingForBoardNoticeResponse> attachList;

}
