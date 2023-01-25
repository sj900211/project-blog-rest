package run.freshr.domain.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.domain.community.enumeration.BoardNoticeExpose;
import run.freshr.domain.community.extension.BoardResponseExtension;

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

}
