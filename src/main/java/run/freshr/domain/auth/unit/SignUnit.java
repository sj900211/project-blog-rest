package run.freshr.domain.auth.unit;

import run.freshr.domain.auth.entity.Sign;

public interface SignUnit {

  Boolean exists(String username);

  Sign get(Long id);

  Sign get(String username);

}
