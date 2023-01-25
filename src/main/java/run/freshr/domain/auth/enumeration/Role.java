package run.freshr.domain.auth.enumeration;

import static java.util.Arrays.stream;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import run.freshr.mappers.EnumModel;

@Slf4j
public enum Role implements EnumModel {

  ROLE_MANAGER_MAJOR("시스템 최상위 관리자", Privilege.MANAGER_MAJOR),
  ROLE_MANAGER_MINOR("시스템 관리자", Privilege.MANAGER_MINOR),
  ROLE_STAFF_MAJOR("최상위 관리자", Privilege.STAFF_MAJOR),
  ROLE_STAFF_MINOR("관리자", Privilege.STAFF_MINOR),
  ROLE_USER("사용자", Privilege.USER),
  ROLE_ANONYMOUS("게스트", Privilege.ANONYMOUS);

  private final String value;
  private final Privilege privilege;

  Role(String value, Privilege privilege) {
    this.value = value;
    this.privilege = privilege;
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

  public boolean check(Role... roles) {
    return Arrays.asList(roles).contains(this);
  }

  public static class Secured {

    public static final String MANAGER_MAJOR = "ROLE_MANAGER_MAJOR";
    public static final String MANAGER_MINOR = "ROLE_MANAGER_MINOR";
    public static final String STAFF_MAJOR = "ROLE_STAFF_MAJOR";
    public static final String STAFF_MINOR = "ROLE_STAFF_MINOR";
    public static final String USER = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

  }

}
