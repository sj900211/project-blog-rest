package run.freshr.domain.common.vo;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.freshr.annotation.SearchClass;
import run.freshr.annotation.SearchComment;
import run.freshr.common.extension.request.SearchExtension;

@Data
@SearchClass
@EqualsAndHashCode(callSuper = true)
public class CommonSearch extends SearchExtension<Long> {

  @SearchComment("해시태그 목록")
  private List<String> hashtags;



}
