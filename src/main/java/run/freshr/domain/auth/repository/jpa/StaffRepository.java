package run.freshr.domain.auth.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.auth.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long>, StaffRepositoryCustom {

}
