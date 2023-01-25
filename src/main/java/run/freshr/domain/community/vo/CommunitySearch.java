package run.freshr.domain.community.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import run.freshr.annotation.SearchClass;
import run.freshr.annotation.SearchComment;
import run.freshr.common.extension.request.SearchExtension;
import run.freshr.domain.community.enumeration.BoardNoticeExpose;

@Data
@SearchClass
@EqualsAndHashCode(callSuper = true)
public class CommunitySearch extends SearchExtension<Long> {

  @SearchComment("상단 고정 여부")
  private Boolean fixed;

  @SearchComment("노출 유형")
  BoardNoticeExpose expose;

}
