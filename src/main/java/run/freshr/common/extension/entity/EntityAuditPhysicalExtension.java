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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.freshr.domain.auth.entity.Account;

/**
 * Audit 정보와 물리 삭제 정책을 가진 MappedSuperclass.
 *
 * @author FreshR
 * @apiNote Audit 정보와 물리 삭제 정책을 가진 MappedSuperclass
 * @since 2023. 1. 12. 오후 6:16:35
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityAuditPhysicalExtension {

  @ColumnDefault(TRUE)
  @Comment("사용 여부")
  protected Boolean useFlag;

  @ColumnDefault(FALSE)
  @Comment("삭제 여부")
  protected Boolean deleteFlag;

  @CreatedDate
  @Comment("등록 날짜")
  protected LocalDateTime createAt;

  @CreatedBy
  @ManyToOne(fetch = LAZY)
  @Comment("등록자 일련 번호")
  @JoinColumn(name = "creator_by")
  protected Account creator;

}
