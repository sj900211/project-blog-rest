package run.freshr.common.config;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import run.freshr.common.security.TokenProvider;
import run.freshr.common.utils.RestUtil;
import run.freshr.domain.auth.entity.Account;

@Component
public class AuditorAwareImpl implements AuditorAware<Account> {

  @NotNull
  @Override
  public Optional<Account> getCurrentAuditor() {
    Long signedId = TokenProvider.signedId.get();
    Account signed = null;

    if (!isNull(signedId) && signedId > 0) {
      signed = RestUtil.getSigned();
    }

    return ofNullable(signed);
  }

}
