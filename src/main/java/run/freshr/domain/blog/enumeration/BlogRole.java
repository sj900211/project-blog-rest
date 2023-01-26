package run.freshr.domain.blog.enumeration;

import java.util.Arrays;
import run.freshr.mappers.EnumModel;

public enum BlogRole implements EnumModel {

  OWNER("관리 권한", true, true, true),
  MAINTAINER("관리 권한", true, true, false),
  REPORTER("포스팅 권한", true, false, false);

  private final String value;
  private final Boolean posting; // 포스팅 작성 권한
  private final Boolean approve; // 접근 요청 관리 권한
  private final Boolean blog; // 블로그 설정 관리 권한

  BlogRole(String value, Boolean posting, Boolean approve, Boolean blog) {
    this.value = value;
    this.posting = posting;
    this.approve = approve;
    this.blog = blog;
  }

  public static BlogRole find(String key) {
    return Arrays.stream(BlogRole.values())
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

  public Boolean getPosting() {
    return posting;
  }

  public Boolean getApprove() {
    return approve;
  }

  public Boolean getBlog() {
    return blog;
  }

}
