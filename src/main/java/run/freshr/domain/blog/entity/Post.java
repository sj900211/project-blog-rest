package run.freshr.domain.blog.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
import run.freshr.domain.mapping.entity.PostAttachMapping;
import run.freshr.domain.mapping.entity.PostHashtagMapping;

@Slf4j
@Entity
@Table(
    name = "TB_BLOG_POST",
    indexes = {
        @Index(name = "IDX_BLOG_POST_FLAG", columnList = "useFlag, deleteFlag")
    }
)
@SequenceGenerator(
    name = "SEQUENCE_GENERATOR_BLOG_POST",
    sequenceName = "SEQ_BLOG_POST"
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "블로그 관리 > 포스트 관리")
public class Post extends EntityAuditLogicalExtension {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "SEQUENCE_GENERATOR_BLOG_POST")
  @Comment("일련 번호")
  private Long id;

  @Column(nullable = false, length = 100)
  @Comment("제목")
  private String title;

  @Lob
  @Column(nullable = false)
  @Comment("내용")
  private String contents;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "blog_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_POST_BLOG"))
  @Comment("블로그 일련 번호")
  private Blog blog;

  @OneToMany(fetch = LAZY, mappedBy = "post")
  private List<PostHashtagMapping> hashtagList;

  @OneToMany(fetch = LAZY, mappedBy = "post")
  private List<PostAttachMapping> attachList;

}
