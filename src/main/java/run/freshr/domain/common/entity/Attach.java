package run.freshr.domain.common.entity;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.utils.BeanUtil.getBean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.net.URL;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extension.entity.EntityAuditLogicalExtension;
import run.freshr.service.MinioService;

@Slf4j
@Entity
@Table(
    name = "TB_COMMON_ATTACH",
    indexes = {
        @Index(name = "IDX_COMMON_ATTACH_FLAG", columnList = "useFlag, deleteFlag")
    }
)
@SequenceGenerator(
    name = "SEQUENCE_GENERATOR_COMMON_ATTACH",
    sequenceName = "SEQ_COMMON_ATTACH"
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "공통 관리 > 파일 관리")
public class Attach extends EntityAuditLogicalExtension {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "SEQUENCE_GENERATOR_COMMON_ATTACH")
  @Comment("일련 번호")
  private Long id;

  @Comment("파일 유형")
  private String contentType;

  @Column(nullable = false)
  @Comment("파일 이름")
  private String filename;

  @Column(nullable = false)
  @Comment("파일 경로")
  private String path;

  @Column(nullable = false)
  @Comment("파일 크기")
  private Long size;

  @Comment("대체 문구")
  private String alt;

  @Comment("제목")
  private String title;

  @Transient
  private URL url;

  private Attach(String contentType, String filename, String path, Long size,
      String alt, String title) {
    log.info("Attach.Constructor");

    this.contentType = contentType;
    this.filename = filename;
    this.path = path;
    this.size = size;
    this.alt = alt;
    this.title = title;
  }

  public static Attach createEntity(String contentType, String filename, String path, Long size,
      String alt, String title) {
    log.info("Attach.createEntity");

    return new Attach(contentType, filename, path, size, alt, title);
  }

  public URL getUrl() throws Exception {
    log.info("Attach.getUrl");

    MinioService service = getBean(MinioService.class);

    return service.getUrl(this.path);
  }

  public void removeEntity() {
    log.info("Attach.removeEntity");

    remove();
  }

}
