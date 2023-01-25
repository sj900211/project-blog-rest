package run.freshr.common.extension.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 물리 삭제 정책을 가진 MappedSuperclass.
 *
 * @author FreshR
 * @apiNote api note
 * @since 2023. 1. 12. 오후 6:19:22
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityPhysicalExtension {

  @CreatedDate
  @Comment("등록 날짜")
  protected LocalDateTime createAt;

}
