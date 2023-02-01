package run.freshr.domain.mapping.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.mapping.embedded.BlogPermissionMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BlogPermissionMapping;

public interface BlogPermissionMappingRepository extends
    JpaRepository<BlogPermissionMapping, BlogPermissionMappingEmbeddedId> {

  boolean existsByIdBlogIdAndIdAccountId(Long blogId, Long accountId);

  Optional<BlogPermissionMapping> findByIdBlogIdAndIdAccountId(Long blogId, Long accountId);

  void deleteByIdBlogIdAndIdAccountId(Long blogId, Long accountId);

}
