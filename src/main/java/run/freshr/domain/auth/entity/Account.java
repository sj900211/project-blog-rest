package run.freshr.domain.auth.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.domain.auth.enumeration.Privilege.USER;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extension.entity.EntityLogicalExtension;
import run.freshr.domain.auth.enumeration.Privilege;

@Slf4j
@Entity
@Table(
    name = "TB_AUTH_ACCOUNT",
    uniqueConstraints = @UniqueConstraint(name = "UK_ACCOUNT_USERNAME", columnNames = {"username"}),
    indexes = {
        @Index(name = "IDX_AUTH_ACCOUNT_PRIVILEGE", columnList = "privilege"),
        @Index(name = "IDX_AUTH_ACCOUNT_FLAG", columnList = "useFlag, deleteFlag")
    }
)
@SequenceGenerator(
    name = "SEQUENCE_GENERATOR_AUTH_ACCOUNT",
    sequenceName = "SEQ_AUTH_ACCOUNT"
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "권한 관리 > 계정 정보 관리")
public class Account extends EntityLogicalExtension {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "SEQUENCE_GENERATOR_AUTH_ACCOUNT")
  @Comment("일련 번호")
  private Long id;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("계정 유형")
  private Privilege privilege;

  @Column(nullable = false, length = 100)
  @Comment("아이디")
  private String username;

  @Column(nullable = false)
  @Comment("비밀번호")
  private String password;

  @Column(nullable = false, length = 100)
  @Comment("이름")
  private String name;

  @Comment("최근 접속 날짜 시간")
  private LocalDateTime signAt;

  @Comment("탈퇴 날짜")
  private LocalDateTime removeAt;

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

  public void updatePrivilege(Privilege privilege) {
    log.info("Account.updatePrivilege");

    this.privilege = privilege;
  }

  public void updatePassword(String password) {
    log.info("Account.updatePassword");

    this.password = password;

    update();
  }

  public void signed() {
    log.info("Account.signed");

    this.signAt = now();
  }

  public void removeEntity() {
    log.info("Account.removeEntity");

    this.username = this.username + "-" + now().format(ofPattern("yyyyMMddHHmmss"));
    this.removeAt = now();

    remove();
  }

}
