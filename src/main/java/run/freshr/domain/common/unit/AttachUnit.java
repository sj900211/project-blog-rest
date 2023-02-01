package run.freshr.domain.common.unit;

import java.util.List;
import run.freshr.common.extension.unit.UnitGetExtension;
import run.freshr.domain.common.entity.Attach;

public interface AttachUnit extends UnitGetExtension<Attach, Long> {

  List<Long> create(List<Attach> entities);

}
