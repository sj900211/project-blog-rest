package run.freshr.domain.mapping.unit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.mapping.embedded.PostHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.PostHashtagMapping;
import run.freshr.domain.mapping.repository.jpa.PostHashtagMappingRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostHashtagMappingUnitImpl implements PostHashtagMappingUnit {

  private final PostHashtagMappingRepository repository;

  @Override
  @Transactional
  public PostHashtagMappingEmbeddedId create(PostHashtagMapping entity) {
    log.info("PostHashtagMappingUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long postId, String hashtagId) {
    log.info("PostHashtagMappingUnit.exists");

    return repository.existsByIdPostIdAndIdHashtagId(postId, hashtagId);
  }

  @Override
  @Transactional
  public PostHashtagMapping get(Long postId, String hashtagId) {
    log.info("PostHashtagMappingUnit.get");

    return repository.findByIdPostIdAndIdHashtagId(postId, hashtagId)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(Post entity) {
    log.info("PostHashtagMappingUnit.delete");

    repository.deleteAllByPost(entity);
  }

}
