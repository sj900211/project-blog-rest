package run.freshr.domain.auth.entity;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.domain.auth.enumeration.Privilege.DELTA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.domain.auth.enumeration.Privilege;

@Slf4j
@Entity
@Table(name = "TB_AUTH_STAFF")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "권한 관리 > 중간 관리자 계정 정보 관리")
public class Staff extends Sign {

  @Column(nullable = false, length = 100)
  @Comment("이름")
  private String name;

  private Staff(Privilege privilege, String username, String password, String name) {
    log.info("Staff.Constructor");

    this.privilege = ofNullable(privilege).orElse(DELTA);
    this.username = username;
    this.password = password;
    this.name = name;
  }

  public static Staff createEntity(Privilege privilege, String username, String password,
      String name) {
    log.info("Staff.createEntity");

    return new Staff(privilege, username, password, name);
  }

  public void updateEntity(String name) {
    log.info("Staff.updateEntity");

    this.name = name;

    update();
  }

}
