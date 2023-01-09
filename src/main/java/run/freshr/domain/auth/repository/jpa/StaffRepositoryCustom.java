package run.freshr.domain.auth.repository.jpa;

import org.springframework.data.domain.Page;
import run.freshr.domain.auth.entity.Staff;
import run.freshr.domain.auth.vo.AuthSearch;

public interface StaffRepositoryCustom {

  Page<Staff> getPage(AuthSearch search);

}
