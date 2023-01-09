package run.freshr.domain.auth.repository.jpa;

import org.springframework.data.domain.Page;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.vo.AuthSearch;

public interface AccountRepositoryCustom {

  Page<Account> getPage(AuthSearch search);

}
