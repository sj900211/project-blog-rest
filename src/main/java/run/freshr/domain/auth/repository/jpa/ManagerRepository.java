package run.freshr.domain.auth.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.auth.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long>, ManagerRepositoryCustom {

}
