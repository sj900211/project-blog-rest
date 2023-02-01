package run.freshr.domain.blog.unit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.repository.jpa.PostRepository;
import run.freshr.domain.blog.vo.BlogSearch;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostUnitImpl implements PostUnit {

  private final PostRepository repository;

  @Override
  @Transactional
  public Long create(Post entity) {
    log.info("PostUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long id) {
    log.info("PostUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Boolean exists(Long blogId, Long id) {
    log.info("PostUnit.exists");

    return repository.existsByBlogIdAndId(blogId, id);
  }

  @Override
  public Post get(Long id) {
    log.info("PostUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Page<Post> getPage(BlogSearch search) {
    log.info("PostUnit.getPage");

    return repository.getPage(search);
  }

}
