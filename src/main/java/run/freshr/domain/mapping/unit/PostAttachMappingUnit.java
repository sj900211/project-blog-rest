package run.freshr.domain.mapping.unit;

import run.freshr.common.extension.unit.UnitDefaultExtension;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.mapping.embedded.PostAttachMappingEmbeddedId;
import run.freshr.domain.mapping.entity.PostAttachMapping;

public interface PostAttachMappingUnit extends
    UnitDefaultExtension<PostAttachMapping, PostAttachMappingEmbeddedId> {

  Boolean exists(Long postId, Long attachId);

  PostAttachMapping get(Long postId, Long attachId);

  void delete(Post entity);

}
