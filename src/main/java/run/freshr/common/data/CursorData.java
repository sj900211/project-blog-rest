package run.freshr.common.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CursorData<T> extends PageImpl<T> {

  private final Long cursor;

  @JsonCreator
  public CursorData(List<T> content, Pageable pageable, long total, Long cursor) {
    super(content, pageable, total);

    this.cursor = cursor;
  }

  public Long getCursor() {
    return cursor;
  }

}
