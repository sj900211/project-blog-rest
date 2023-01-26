package run.freshr.domain.common.access;

import static run.freshr.common.utils.RestUtil.checkManager;
import static run.freshr.common.utils.RestUtil.getSignedId;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.unit.AttachUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonAccess {

  private final AttachUnit attachUnit;

  public boolean removeAttach(Long id) {
    log.info("CommonAccess.removeAttach");

    if (checkManager()) {
      return true;
    }

    Attach entity = attachUnit.get(id);

    Account owner = entity.getCreator();
    Long signedId = getSignedId();

    return Objects.equals(owner.getId(), signedId);
  }

}
