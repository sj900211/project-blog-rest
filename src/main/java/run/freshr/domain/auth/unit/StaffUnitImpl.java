package run.freshr.domain.auth.unit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.auth.entity.Staff;
import run.freshr.domain.auth.repository.jpa.StaffRepository;
import run.freshr.domain.auth.vo.AuthSearch;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StaffUnitImpl implements StaffUnit {

  private final StaffRepository repository;

  @Override
  @Transactional
  public Long create(Staff entity) {
    log.info("StaffUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long id) {
    log.info("StaffUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Staff get(Long id) {
    log.info("StaffUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Page<Staff> getPage(AuthSearch search) {
    log.info("StaffUnit.getPage");

    return repository.getPage(search);
  }

}
