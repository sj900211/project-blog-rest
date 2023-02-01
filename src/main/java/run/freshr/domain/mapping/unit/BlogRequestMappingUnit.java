package run.freshr.domain.mapping.unit;

import run.freshr.common.extension.unit.UnitDefaultExtension;
import run.freshr.domain.mapping.embedded.BlogRequestMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BlogRequestMapping;

public interface BlogRequestMappingUnit extends
    UnitDefaultExtension<BlogRequestMapping, BlogRequestMappingEmbeddedId> {

  Boolean exists(Long blogId, Long accountId);

  BlogRequestMapping get(Long blogId, Long accountId);

  void delete(Long blogId, Long accountId);

}
