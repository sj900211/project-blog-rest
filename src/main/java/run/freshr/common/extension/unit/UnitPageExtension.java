package run.freshr.common.extension.unit;

import org.springframework.data.domain.Page;
import run.freshr.common.extension.request.SearchExtension;

public interface UnitPageExtension<E, ID, S extends SearchExtension<ID>>
    extends UnitDefaultExtension<E, ID> {

  Page<E> getPage(S search);

}
