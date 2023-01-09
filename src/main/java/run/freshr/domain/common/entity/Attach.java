package run.freshr.domain.common.entity;

import static lombok.AccessLevel.PROTECTED;
import static run.freshr.utils.BeanUtil.getBean;

import java.net.URL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.annotation.TableComment;
import run.freshr.common.extension.entity.EntityAuditLogicalExtension;
import run.freshr.domain.auth.entity.Sign;
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
    name = "SEQUENCE_GENERATOR",
    sequenceName = "SEQ_COMMON_ATTACH"
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@TableComment(value = "공통 관리 > 파일 관리")
public class Attach extends EntityAuditLogicalExtension {

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
      String alt, String title, Sign creator) {
    log.info("Attach.Constructor");

    this.contentType = contentType;
    this.filename = filename;
    this.path = path;
    this.size = size;
    this.alt = alt;
    this.title = title;
    this.creator = creator;
    this.updater = creator;
  }

  public static Attach createEntity(String contentType, String filename, String path, Long size,
      String alt, String title, Sign creator) {
    log.info("Attach.createEntity");

    return new Attach(contentType, filename, path, size, alt, title, creator);
  }

  public URL getUrl() throws Exception {
    log.info("Attach.getUrl");

    MinioService service = getBean(MinioService.class);

    return service.getUrl(this.path);
  }

  public void removeEntity(Sign updater) {
    log.info("Attach.removeEntity");

    remove(updater);
  }

}
