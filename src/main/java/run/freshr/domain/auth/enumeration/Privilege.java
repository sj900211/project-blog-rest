package run.freshr.domain.auth.enumeration;

import lombok.extern.slf4j.Slf4j;
import run.freshr.mappers.EnumModel;

import static java.util.Arrays.stream;
import static run.freshr.domain.auth.enumeration.Role.*;

/**
 * Privilege.
 *
 * @author FreshR
 * @apiNote 권한 목록
 * @since 2022. 12. 23. 오후 4:23:59
 */
@Slf4j
public enum Privilege implements EnumModel {

  ALPHA("시스템 관리자", ROLE_ALPHA),
  BETA("최상위 관리자", ROLE_BETA),
  GAMMA("상위 관리자", ROLE_DELTA),
  DELTA("관리자", ROLE_DELTA),
  USER("사용자", ROLE_USER),
  ANONYMOUS("게스트", ROLE_ANONYMOUS);

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
  Privilege(String value, Role role) {
    this.value = value;
    this.role = role;
  }

  public static Privilege find(String key) {
    log.info("Privilege.find");

    return stream(Privilege.values())
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

  public Role getRole() {
    return this.role;
  }

}
