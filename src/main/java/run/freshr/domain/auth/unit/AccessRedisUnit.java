package run.freshr.domain.auth.unit;

import run.freshr.common.extension.unit.UnitDocumentDefaultExtension;
import run.freshr.domain.auth.redis.AccessRedis;

public interface AccessRedisUnit extends UnitDocumentDefaultExtension<AccessRedis, String> {

  Boolean exists(Long signId);

  AccessRedis get(Long signId);

  Iterable<AccessRedis> getList();

  void delete(Long signId);

}
