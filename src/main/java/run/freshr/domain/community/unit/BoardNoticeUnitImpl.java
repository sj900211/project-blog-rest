package run.freshr.domain.community.unit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.community.entity.BoardNotice;
import run.freshr.domain.community.repository.jpa.BoardNoticeRepository;
import run.freshr.domain.community.vo.CommunitySearch;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardNoticeUnitImpl implements BoardNoticeUnit {

  private final BoardNoticeRepository repository;

  @Override
  @Transactional
  public Long create(BoardNotice entity) {
    log.info("BoardNoticeUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long id) {
    log.info("BoardNoticeUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public BoardNotice get(Long id) {
    log.info("BoardNoticeUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Page<BoardNotice> getPage(CommunitySearch search) {
    log.info("BoardNoticeUnit.getPage");

    return repository.getPage(search);
  }

}
