package run.freshr.domain.mapping.unit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.mapping.embedded.BlogPermissionMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BlogPermissionMapping;
import run.freshr.domain.mapping.repository.jpa.BlogPermissionMappingRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogPermissionMappingUnitImpl implements BlogPermissionMappingUnit {

  private final BlogPermissionMappingRepository repository;

  @Override
  @Transactional
  public BlogPermissionMappingEmbeddedId create(BlogPermissionMapping entity) {
    log.info("BlogPermissionMappingUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long blogId, Long accountId) {
    log.info("BlogPermissionMappingUnit.exists");

    return repository.existsByIdBlogIdAndIdAccountId(blogId, accountId);
  }

  @Override
  @Transactional
  public BlogPermissionMapping get(Long blogId, Long accountId) {
    log.info("BlogPermissionMappingUnit.get");

    return repository.findByIdBlogIdAndIdAccountId(blogId, accountId)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(Long blogId, Long accountId) {
    log.info("BlogPermissionMappingUnit.delete");

    repository.deleteByIdBlogIdAndIdAccountId(blogId, accountId);
  }

}
