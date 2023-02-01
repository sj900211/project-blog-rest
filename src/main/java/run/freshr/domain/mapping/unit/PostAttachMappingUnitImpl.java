package run.freshr.domain.mapping.unit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.mapping.embedded.PostAttachMappingEmbeddedId;
import run.freshr.domain.mapping.entity.PostAttachMapping;
import run.freshr.domain.mapping.repository.jpa.PostAttachMappingRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostAttachMappingUnitImpl implements PostAttachMappingUnit {

  private final PostAttachMappingRepository repository;

  @Override
  @Transactional
  public PostAttachMappingEmbeddedId create(PostAttachMapping entity) {
    log.info("PostAttachMappingUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long postId, Long attachId) {
    log.info("PostAttachMappingUnit.exists");

    return repository.existsByIdPostIdAndIdAttachId(postId, attachId);
  }

  @Override
  @Transactional
  public PostAttachMapping get(Long postId, Long attachId) {
    log.info("PostAttachMappingUnit.get");

    return repository.findByIdPostIdAndIdAttachId(postId, attachId)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(Post entity) {
    log.info("PostAttachMappingUnit.delete");

    repository.deleteAllByPost(entity);
  }

}
