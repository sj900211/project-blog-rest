package run.freshr.domain.blog.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extension.entity.EntityAuditLogicalExtension;
import run.freshr.domain.blog.enumeration.BlogViewType;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.mapping.entity.BlogHashtagMapping;
import run.freshr.domain.mapping.entity.BlogRoleMapping;

@Slf4j
@Entity
@Table(
    name = "TB_BLOG_BLOG",
    indexes = {
        @Index(name = "IDX_BLOG_BLOG_FLAG", columnList = "useFlag, deleteFlag")
    }
)
@SequenceGenerator(
    name = "SEQUENCE_GENERATOR_BLOG_BLOG",
    sequenceName = "SEQ_BLOG_BLOG"
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "블로그 관리 > 포스트 관리")
public class Blog extends EntityAuditLogicalExtension {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "SEQUENCE_GENERATOR_BLOG_BLOG")
  @Comment("일련 번호")
  private Long id;

  @Column(nullable = false, length = 100)
  @Comment("제목")
  private String title;

  @Lob
  @Column(nullable = false)
  @Comment("설명")
  private String description;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("공개 유형")
  private BlogViewType viewType;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "cover_id", foreignKey = @ForeignKey(name = "FK_BLOG_COVER"))
  @Comment("커버 이미지 일련 번호")
  private Attach cover;

  @OneToMany(fetch = LAZY, mappedBy = "blog")
  private List<BlogHashtagMapping> hashtagList;

  @OneToMany(fetch = LAZY, mappedBy = "blog")
  private List<BlogRoleMapping> roleList;

  @OneToMany(fetch = LAZY, mappedBy = "blog")
  private List<Post> postList;

  private Blog(String title, String description, Attach cover, BlogViewType viewType) {
    log.info("Blog.Constructor");

    this.title = title;
    this.description = description;
    this.cover = cover;
    this.viewType = viewType;
  }

  public static Blog createEntity(String title, String description, Attach cover,
      BlogViewType viewType) {
    log.info("Blog.createEntity");

    return new Blog(title, description, cover, viewType);
  }

  public void updateEntity(String title, String description, Attach cover, BlogViewType viewType) {
    log.info("Blog.updateEntity");

    this.title = title;
    this.description = description;
    this.cover = cover;
    this.viewType = viewType;
  }

  public void removeEntity() {
    log.info("Blog.removeEntity");

    remove();
  }

}
