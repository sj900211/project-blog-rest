package run.freshr.domain.common.unit;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.common.dto.response.HashtagStatisticsResponse;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.common.repository.jpa.HashtagRepository;
import run.freshr.domain.common.vo.CommonSearch;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagUnitImpl implements HashtagUnit {

  private final HashtagRepository repository;

  @Override
  @Transactional
  public String create(Hashtag entity) {
    log.info("HashtagUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(String id) {
    log.info("HashtagUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Hashtag get(String id) {
    log.info("HashtagUnit.get");

    return repository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public List<Hashtag> getList() {
    log.info("HashtagUnit.getList");

    return repository.findAllByOrderByIdAsc();
  }

  @Override
  public List<Hashtag> search(String keyword) {
    log.info("HashtagUnit.search");

    return repository.findAllByIdContainsIgnoreCase(keyword);
  }

  @Override
  public List<HashtagStatisticsResponse> getStatistics() {
    log.info("HashtagUnit.getStatistics");

    return repository.getStatistics();
  }

  @Override
  public Page<Blog> getBlogPage(CommonSearch search) {
    log.info("HashtagUnit.getBlogPage");

    return repository.getBlogPage(search);
  }

  @Override
  public Page<Post> getPostPage(CommonSearch search) {
    log.info("HashtagUnit.getPostPage");

    return repository.getPostPage(search);
  }

}
