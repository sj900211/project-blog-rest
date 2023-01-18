package run.freshr.common.extension.entity;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.freshr.domain.auth.entity.Sign;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static run.freshr.common.config.DefaultColumnConfig.FALSE;
import static run.freshr.common.config.DefaultColumnConfig.TRUE;

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

  /**
   * Check owner boolean.
   *
   * @param entity entity
   * @return boolean
   * @apiNote 데이터 작성자 본인인지 체크
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:18:15
   */
  public boolean checkOwner(Sign entity) {
    return creator.equals(entity);
  }

}
