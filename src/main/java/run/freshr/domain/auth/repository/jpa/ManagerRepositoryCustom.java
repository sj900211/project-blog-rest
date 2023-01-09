package run.freshr.domain.auth.repository.jpa;

import org.springframework.data.domain.Page;
import run.freshr.domain.auth.entity.Manager;
import run.freshr.domain.auth.vo.AuthSearch;

public interface ManagerRepositoryCustom {

  Page<Manager> getPage(AuthSearch search);

}
