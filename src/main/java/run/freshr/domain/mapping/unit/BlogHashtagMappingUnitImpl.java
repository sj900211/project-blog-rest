package run.freshr.domain.mapping.unit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.mapping.embedded.BlogHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BlogHashtagMapping;
import run.freshr.domain.mapping.repository.jpa.BlogHashtagMappingRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogHashtagMappingUnitImpl implements BlogHashtagMappingUnit {

  private final BlogHashtagMappingRepository repository;

  @Override
  @Transactional
  public BlogHashtagMappingEmbeddedId create(BlogHashtagMapping entity) {
    log.info("BlogHashtagMappingUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long blogId, String hashtagId) {
    log.info("BlogHashtagMappingUnit.exists");

    return repository.existsByIdBlogIdAndIdHashtagId(blogId, hashtagId);
  }

  @Override
  @Transactional
  public BlogHashtagMapping get(Long blogId, String hashtagId) {
    log.info("BlogHashtagMappingUnit.get");

    return repository.findByIdBlogIdAndIdHashtagId(blogId, hashtagId)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(Blog entity) {
    log.info("BlogHashtagMappingUnit.delete");

    repository.deleteAllByBlog(entity);
  }

}
