package run.freshr.common.extension.entity;

import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static run.freshr.common.config.DefaultColumnConfig.FALSE;
import static run.freshr.common.config.DefaultColumnConfig.TRUE;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.freshr.domain.auth.entity.Sign;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityAuditLogicalExtension {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Comment("일련 번호")
  protected Long id;

  @ColumnDefault(TRUE)
  @Comment("사용 여부")
  protected Boolean useFlag;

  @ColumnDefault(FALSE)
  @Comment("삭제 여부")
  protected Boolean deleteFlag;

  @CreatedDate
  @Comment("등록 날짜")
  protected LocalDateTime createAt;

  @ManyToOne(fetch = LAZY)
  @Comment("등록자 일련 번호")
  @JoinColumn(name = "creator_id")
  protected Sign creator;

  @LastModifiedDate
  @Comment("마지막 수정 날짜")
  protected LocalDateTime updateAt;

  @ManyToOne(fetch = LAZY)
  @Comment("수정자 일련 번호")
  @JoinColumn(name = "updater_id")
  protected Sign updater;

  protected void remove(Sign updater) {
    this.updater = updater;
    this.useFlag = false;
    this.deleteFlag = true;
  }

  protected void update(Sign updater) {
    this.updater = updater;
    this.updateAt = now();
  }

  public boolean checkOwner(Sign entity) {
    return creator.equals(entity);
  }

}
