package run.freshr.domain.auth.unit;

import run.freshr.common.extension.unit.UnitDocumentDefaultExtension;
import run.freshr.domain.auth.redis.RsaPair;

public interface RsaPairUnit extends UnitDocumentDefaultExtension<RsaPair, String> {

  Iterable<RsaPair> getList();

  void delete(Iterable<RsaPair> list);

  Boolean checkRsa(String encodePublicKey);

}
