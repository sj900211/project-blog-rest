package run.freshr.domain.mapping.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.mapping.embedded.BlogHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BlogHashtagMapping;

public interface BlogHashtagMappingRepository extends
    JpaRepository<BlogHashtagMapping, BlogHashtagMappingEmbeddedId> {

  boolean existsByIdBlogIdAndIdHashtagId(Long blogId, String hashtagId);

  Optional<BlogHashtagMapping> findByIdBlogIdAndIdHashtagId(Long blogId, String hashtagId);

  void deleteAllByBlog(Blog entity);

}
