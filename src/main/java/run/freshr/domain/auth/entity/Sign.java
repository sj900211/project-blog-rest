package run.freshr.domain.auth.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.InheritanceType.JOINED;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
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
    name = "TB_AUTH_SIGN",
    uniqueConstraints = @UniqueConstraint(name = "UK_ACCOUNT_USERNAME", columnNames = {"username"}),
    indexes = {
        @Index(name = "IDX_AUTH_ACCOUNT_PRIVILEGE", columnList = "privilege"),
        @Index(name = "IDX_AUTH_ACCOUNT_FLAG", columnList = "useFlag, deleteFlag")
    }
)
@SequenceGenerator(
    name="SEQUENCE_GENERATOR",
    sequenceName="SEQ_AUTH_ACCOUNT"
)
@Getter
@DynamicInsert
@DynamicUpdate
@Inheritance(strategy = JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "권한 관리 > 계정 관리")
public class Sign extends EntityLogicalExtension {

    @Enumerated(STRING)
    @Column(nullable = false)
    @Comment("계정 유형")
    protected Privilege privilege;

    @Column(nullable = false, length = 100)
    @Comment("아이디")
    protected String username;

    @Column(nullable = false)
    @Comment("비밀번호")
    protected String password;

    @Comment("최근 접속 날짜 시간")
    protected LocalDateTime signAt;

    @Comment("탈퇴 날짜")
    protected LocalDateTime removeAt;

    public void signed() {
        log.info("Sign.signed");

        this.signAt = now();
    }

    public void updatePassword(String password) {
        log.info("Sign.updatePassword");

        this.password = password;

        update();
    }

    public void removeEntity() {
        log.info("Sign.removeEntity");

        this.username = this.username + "-" + now().format(ofPattern("yyyyMMddHHmmss"));
        this.removeAt = now();

        remove();
    }

}
