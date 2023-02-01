package run.freshr.domain.blog.unit;

import run.freshr.common.extension.unit.UnitPageExtension;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.vo.BlogSearch;

public interface PostUnit extends UnitPageExtension<Post, Long, BlogSearch> {

  Boolean exists(Long blogId, Long id);

}
