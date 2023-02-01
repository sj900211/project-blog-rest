package run.freshr.domain.mapping.unit;

import run.freshr.common.extension.unit.UnitDefaultExtension;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.mapping.embedded.BlogHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BlogHashtagMapping;

public interface BlogHashtagMappingUnit extends
    UnitDefaultExtension<BlogHashtagMapping, BlogHashtagMappingEmbeddedId> {

  Boolean exists(Long blogId, String hashtagId);

  BlogHashtagMapping get(Long blogId, String hashtagId);

  void delete(Blog entity);

}
