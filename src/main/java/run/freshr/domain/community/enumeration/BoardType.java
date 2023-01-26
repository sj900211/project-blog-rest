package run.freshr.domain.community.enumeration;

import java.util.Arrays;
import run.freshr.mappers.EnumModel;

public enum BoardType implements EnumModel {

  NOTICE("공지사항");

  private final String value;

  BoardType(String value) {
    this.value = value;
  }

  public static BoardType find(String key) {
    return Arrays.stream(BoardType.values())
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

  public static class Discriminator {

    public static final String NOTICE = "NOTICE";
  }

}
