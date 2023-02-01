package run.freshr.domain.mapping.unit;

import run.freshr.common.extension.unit.UnitDefaultExtension;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.mapping.embedded.PostHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.PostHashtagMapping;

public interface PostHashtagMappingUnit extends
    UnitDefaultExtension<PostHashtagMapping, PostHashtagMappingEmbeddedId> {

  Boolean exists(Long postId, String hashtagId);

  PostHashtagMapping get(Long postId, String hashtagId);

  void delete(Post entity);

}
