package run.freshr.domain.auth.entity;

import static lombok.AccessLevel.PROTECTED;
import static run.freshr.domain.auth.enumeration.Privilege.USER;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Slf4j
@Entity
@Table(name = "TB_AUTH_ACCOUNT")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "권한 관리 > 계정 정보 관리")
public class Account extends Sign {

  @Column(nullable = false, length = 100)
  @Comment("이름")
  private String name;

  private Account(String username, String password, String name) {
    log.info("Account.Constructor");

    this.privilege = USER;
    this.username = username;
    this.password = password;
    this.name = name;
  }

  public static Account createEntity(String username, String password, String name) {
    log.info("Account.createEntity");

    return new Account(username, password, name);
  }

  public void updateEntity(String name) {
    log.info("Account.updateEntity");

    this.name = name;

    update();
  }

}
