package run.freshr.common.config;

import static java.util.Objects.isNull;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import run.freshr.common.security.TokenProvider;
import run.freshr.common.utils.RestUtil;
import run.freshr.domain.auth.entity.Sign;

@Component
public class AuditorAwareImpl implements AuditorAware<Sign> {

  @NotNull
  @Override
  public Optional<Sign> getCurrentAuditor() {
    Long signedId = TokenProvider.signedId.get();
    Sign signed = null;

    if (!isNull(signedId) && signedId > 0) {
      signed = RestUtil.getSigned();
    }

    return Optional.ofNullable(signed);
  }

}
