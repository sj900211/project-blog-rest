package run.freshr.domain.auth.unit;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.auth.entity.Manager;
import run.freshr.domain.auth.repository.jpa.ManagerRepository;
import run.freshr.domain.auth.vo.AuthSearch;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerUnitImpl implements ManagerUnit {

  private final ManagerRepository repository;

  @Override
  @Transactional
  public Long create(Manager entity) {
    log.info("ManagerUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long id) {
    log.info("ManagerUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Manager get(Long id) {
    log.info("ManagerUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Page<Manager> getPage(AuthSearch search) {
    log.info("ManagerUnit.getPage");

    return repository.getPage(search);
  }

}
