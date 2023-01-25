package run.freshr.domain.community.enumeration;

import static java.util.Arrays.stream;

import lombok.extern.slf4j.Slf4j;
import run.freshr.mappers.EnumModel;

@Slf4j
public enum BoardNoticeOrderTypes implements EnumModel {

  TITLE("제목"),
  FIXED("상단 고정 여부"),
  EXPOSE("노출 유형"),
  CREATOR("작성자 이름");

  private final String value;

  BoardNoticeOrderTypes(String value) {
    this.value = value;
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return value;
  }

  public static BoardNoticeOrderTypes find(String key) {
    log.info("BoardNoticeOrderTypes.find");

    return stream(BoardNoticeOrderTypes.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(null);
  }

}
