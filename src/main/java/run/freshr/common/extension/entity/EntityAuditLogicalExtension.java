package run.freshr.common.extension.entity;

import static jakarta.persistence.FetchType.LAZY;
import static run.freshr.common.config.DefaultColumnConfig.FALSE;
import static run.freshr.common.config.DefaultColumnConfig.TRUE;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.freshr.domain.auth.entity.Account;

/**
 * Audit 정보와 논리 삭제 정책을 가진 MappedSuperclass.
 *
 * @author FreshR
 * @apiNote Audit 정보와 논리 삭제 정책을 가진 MappedSuperclass
 * @since 2023. 1. 12. 오후 6:16:35
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityAuditLogicalExtension {

  @ColumnDefault(TRUE)
  @Comment("사용 여부")
  protected Boolean useFlag;

  @ColumnDefault(FALSE)
  @Comment("삭제 여부")
  protected Boolean deleteFlag;

  @CreatedDate
  @Comment("등록 날짜")
  protected LocalDateTime createAt;

  @LastModifiedDate
  @Comment("마지막 수정 날짜")
  protected LocalDateTime updateAt;

  @CreatedBy
  @ManyToOne(fetch = LAZY)
  @Comment("등록자 일련 번호")
  @JoinColumn(name = "creator_by")
  protected Account creator;

  @LastModifiedBy
  @ManyToOne(fetch = LAZY)
  @Comment("수정자 일련 번호")
  @JoinColumn(name = "updater_by")
  protected Account updater;

  /**
   * Remove.
   *
   * @apiNote 논리 삭제에 대한 공통 처리
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:17:40
   */
  protected void remove() {
    this.useFlag = false;
    this.deleteFlag = true;
  }

}
