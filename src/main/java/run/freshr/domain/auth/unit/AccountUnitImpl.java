package run.freshr.domain.auth.unit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.repository.jpa.AccountRepository;
import run.freshr.domain.auth.vo.AuthSearch;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountUnitImpl implements AccountUnit {

  private final AccountRepository repository;

  @Override
  @Transactional
  public Long create(Account entity) {
    log.info("AccountUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long id) {
    log.info("AccountUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Account get(Long id) {
    log.info("AccountUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Page<Account> getPage(AuthSearch search) {
    log.info("AccountUnit.getPage");

    return repository.getPage(search);
  }

}
