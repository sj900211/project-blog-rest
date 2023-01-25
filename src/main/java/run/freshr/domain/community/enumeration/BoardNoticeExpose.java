package run.freshr.domain.community.enumeration;

import java.util.Arrays;
import run.freshr.mappers.EnumModel;

public enum BoardNoticeExpose implements EnumModel {

  ALL("전체 노출"),
  MANAGER("시스템 관리자 노출"),
  MANAGER_STAFF("시스템 관리자 & 관리자 노출"),
  MANAGER_USER("시스템 관리자 & 사용자 노출"),
  STAFF_USER("관리자 & 사용자 노출"),
  USER("사용자 노출");

  private final String value;

  BoardNoticeExpose(String value) {
    this.value = value;
  }

  public static BoardNoticeExpose find(String key) {
    return Arrays.stream(BoardNoticeExpose.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
