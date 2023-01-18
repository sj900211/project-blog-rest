package run.freshr.common.extension.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;
import static run.freshr.common.config.DefaultColumnConfig.FALSE;
import static run.freshr.common.config.DefaultColumnConfig.TRUE;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 논리 삭제 정책을 가진 MappedSuperclass.
 *
 * @author FreshR
 * @apiNote 논리 삭제 정책을 가진 MappedSuperclass
 * @since 2023. 1. 12. 오후 6:18:35
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityLogicalExtension {

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

  @LastModifiedDate
  @Comment("마지막 수정 날짜")
  protected LocalDateTime updateAt;

  /**
   * Remove.
   *
   * @apiNote 논리 삭제에 대한 공통 처리
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:18:57
   */
  protected void remove() {
    this.useFlag = false;
    this.deleteFlag = true;
  }

  /**
   * Update.
   *
   * @apiNote 수정에 대한 공통 처리
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:19:16
   */
  protected void update() {
    this.updateAt = now();
  }

}
