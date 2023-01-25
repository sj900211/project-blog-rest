package run.freshr.domain.auth.enumeration;

import static java.util.Arrays.stream;
import static run.freshr.domain.auth.enumeration.Role.ROLE_STAFF_MINOR;

import lombok.extern.slf4j.Slf4j;
import run.freshr.mappers.EnumModel;

/**
 * Privilege.
 *
 * @author FreshR
 * @apiNote 권한 목록
 * @since 2022. 12. 23. 오후 4:23:59
 */
@Slf4j
public enum PrivilegeStaff implements EnumModel {

  STAFF_MINOR("관리자", ROLE_STAFF_MINOR);

  private final String value;
  private final Role role;

  /**
   * Instantiates a new Privilege.
   *
   * @param value value
   * @apiNote api note
   * @author FreshR
   * @since 2022. 12. 23. 오후 4:23:59
   */
  PrivilegeStaff(String value, Role role) {
    this.value = value;
    this.role = role;
  }

  public static PrivilegeStaff find(String key) {
    log.info("Privilege.find");

    return stream(PrivilegeStaff.values())
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
    return this.value;
  }

}
