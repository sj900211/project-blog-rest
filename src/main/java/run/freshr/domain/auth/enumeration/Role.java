package run.freshr.domain.auth.enumeration;

import static java.util.Arrays.stream;

import lombok.extern.slf4j.Slf4j;
import run.freshr.mappers.EnumModel;

@Slf4j
public enum Role implements EnumModel {

  ROLE_ALPHA("시스템 관리자", Privilege.ALPHA, 0B100000),
  ROLE_BETA("최상위 관리자", Privilege.BETA, 0B010000),
  ROLE_GAMMA("상위 관리자", Privilege.GAMMA, 0B001000),
  ROLE_DELTA("관리자", Privilege.DELTA, 0B000100),
  ROLE_USER("사용자", Privilege.USER, 0B000010),
  ROLE_ANONYMOUS("게스트", Privilege.ANONYMOUS, 0B000001);

  private final String value;
  private final Privilege privilege;
  private final Integer permission;

  Role(String value, Privilege privilege, Integer permission) {
    this.value = value;
    this.privilege = privilege;
    this.permission = permission;
  }

  public static Role find(String key) {
    log.info("Role.find");

    return stream(Role.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(null);
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return value;
  }

  public Privilege getPrivilege() {
    return privilege;
  }

  public Integer getPermission() {
    return permission;
  }

  public static class Secured {

    public static final String ALPHA = "ROLE_ALPHA";
    public static final String BETA = "ROLE_BETA";
    public static final String GAMMA = "ROLE_GAMMA";
    public static final String DELTA = "ROLE_DELTA";
    public static final String USER = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

  }

}
