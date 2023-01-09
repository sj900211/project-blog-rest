package run.freshr.domain.auth.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.auth.entity.Sign;

public interface SignRepository extends JpaRepository<Sign, Long> {

  Boolean existsByUsername(String username);

  Sign findByUsername(String username);

}
