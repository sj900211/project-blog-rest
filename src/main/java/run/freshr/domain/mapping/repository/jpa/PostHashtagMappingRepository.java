package run.freshr.domain.mapping.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.mapping.embedded.PostHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.PostHashtagMapping;

public interface PostHashtagMappingRepository extends
    JpaRepository<PostHashtagMapping, PostHashtagMappingEmbeddedId> {

  boolean existsByIdPostIdAndIdHashtagId(Long postId, String hashtagId);

  Optional<PostHashtagMapping> findByIdPostIdAndIdHashtagId(Long postId, String hashtagId);

  void deleteAllByPost(Post entity);

}
