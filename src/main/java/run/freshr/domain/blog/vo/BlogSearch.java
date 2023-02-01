package run.freshr.domain.blog.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import run.freshr.annotation.SearchClass;
import run.freshr.annotation.SearchComment;
import run.freshr.common.extension.request.SearchExtension;

@Data
@SearchClass
@EqualsAndHashCode(callSuper = true)
public class BlogSearch extends SearchExtension<Long> {

  @SearchComment("블로그 일련 번호")
  private Long blogId;

}
