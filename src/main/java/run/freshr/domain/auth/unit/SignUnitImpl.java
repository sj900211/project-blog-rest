package run.freshr.domain.auth.unit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotations.Unit;
import run.freshr.domain.auth.entity.Sign;
import run.freshr.domain.auth.repository.jpa.SignRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignUnitImpl implements SignUnit {

  private final SignRepository repository;

  @Override
  public Boolean exists(String username) {
    log.info("SignUnit.exists");

    return repository.existsByUsername(username);
  }

  @Override
  public Sign get(Long id) {
    log.info("SignUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Sign get(String username) {
    log.info("SignUnit.get");

    return repository.findByUsername(username);
  }

}
