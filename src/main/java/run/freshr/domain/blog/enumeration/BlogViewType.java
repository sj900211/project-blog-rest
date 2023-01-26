package run.freshr.domain.blog.enumeration;

import java.util.Arrays;
import run.freshr.mappers.EnumModel;

public enum BlogViewType implements EnumModel {

  PUBLIC("공개"),
  PRIVATE("비공개");

  private final String value;

  BlogViewType(String value) {
    this.value = value;
  }

  public static BlogViewType find(String key) {
    return Arrays.stream(BlogViewType.values())
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
