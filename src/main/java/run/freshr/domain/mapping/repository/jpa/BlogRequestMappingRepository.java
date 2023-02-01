package run.freshr.domain.mapping.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.mapping.embedded.BlogRequestMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BlogRequestMapping;

public interface BlogRequestMappingRepository extends
    JpaRepository<BlogRequestMapping, BlogRequestMappingEmbeddedId> {

  boolean existsByIdBlogIdAndIdAccountId(Long blogId, Long accountId);

  Optional<BlogRequestMapping> findByIdBlogIdAndIdAccountId(Long blogId, Long accountId);

  void deleteByIdBlogIdAndIdAccountId(Long blogId, Long accountId);

}
