package run.freshr.domain.mapping.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.mapping.embedded.PostAttachMappingEmbeddedId;
import run.freshr.domain.mapping.entity.PostAttachMapping;

public interface PostAttachMappingRepository extends
    JpaRepository<PostAttachMapping, PostAttachMappingEmbeddedId> {

  boolean existsByIdPostIdAndIdAttachId(Long postId, Long attachId);

  Optional<PostAttachMapping> findByIdPostIdAndIdAttachId(Long postId, Long attachId);

  void deleteAllByPost(Post entity);

}
