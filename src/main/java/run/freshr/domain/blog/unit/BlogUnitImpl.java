package run.freshr.domain.blog.unit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.repository.jpa.BlogRepository;
import run.freshr.domain.blog.vo.BlogSearch;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogUnitImpl implements BlogUnit {

  private final BlogRepository repository;

  @Override
  @Transactional
  public Long create(Blog entity) {
    log.info("BlogUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long id) {
    log.info("BlogUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Blog get(Long id) {
    log.info("BlogUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Page<Blog> getPage(BlogSearch search) {
    log.info("BlogUnit.getPage");

    return repository.getPage(search);
  }

}
