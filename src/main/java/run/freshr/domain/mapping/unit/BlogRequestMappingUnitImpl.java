package run.freshr.domain.mapping.unit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.mapping.embedded.BlogRequestMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BlogRequestMapping;
import run.freshr.domain.mapping.repository.jpa.BlogRequestMappingRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogRequestMappingUnitImpl implements BlogRequestMappingUnit {

  private final BlogRequestMappingRepository repository;

  @Override
  @Transactional
  public BlogRequestMappingEmbeddedId create(BlogRequestMapping entity) {
    log.info("BlogRequestMappingUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long blogId, Long accountId) {
    log.info("BlogRequestMappingUnit.exists");

    return repository.existsByIdBlogIdAndIdAccountId(blogId, accountId);
  }

  @Override
  @Transactional
  public BlogRequestMapping get(Long blogId, Long accountId) {
    log.info("BlogRequestMappingUnit.get");

    return repository.findByIdBlogIdAndIdAccountId(blogId, accountId)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(Long blogId, Long accountId) {
    log.info("BlogRequestMappingUnit.delete");

    repository.deleteByIdBlogIdAndIdAccountId(blogId, accountId);
  }

}
