package run.freshr.common.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Cursor Page.
 *
 * @param <T> 데이터 객체
 * @author FreshR
 * @apiNote Cursor 기능을 사용할 때 Page 객체에 Cursor 정보를 추가
 * @since 2023. 1. 12. 오후 6:02:20
 */
public class CursorData<T> extends PageImpl<T> {

  /**
   * Cursor
   *
   * @apiNote Cursor 값
   * @since 2023. 1. 12. 오후 6:02:20
   */
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
