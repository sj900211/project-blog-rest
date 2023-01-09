package run.freshr.domain.auth.unit;

import run.freshr.common.extension.unit.UnitDocumentDefaultExtension;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;

public interface RefreshRedisUnit extends UnitDocumentDefaultExtension<RefreshRedis, String> {

  void delete(AccessRedis access);

}
