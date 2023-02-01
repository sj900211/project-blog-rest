package run.freshr.domain.mapping.unit;

import run.freshr.common.extension.unit.UnitDefaultExtension;
import run.freshr.domain.mapping.embedded.BlogPermissionMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BlogPermissionMapping;

public interface BlogPermissionMappingUnit extends
    UnitDefaultExtension<BlogPermissionMapping, BlogPermissionMappingEmbeddedId> {

  Boolean exists(Long blogId, Long accountId);

  BlogPermissionMapping get(Long blogId, Long accountId);

  void delete(Long blogId, Long accountId);

}
