package run.freshr.domain.mapping.entity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.common.config.DefaultColumnConfig.ZERO;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.mapping.embedded.PostAttachMappingEmbeddedId;

@Slf4j
@Entity
@Table(name = "TB_MAP_POST_ATTACH")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "연관 관계 관리 > 포스팅 첨부파일 관리")
public class PostAttachMapping {

  @EmbeddedId
  private PostAttachMappingEmbeddedId id;

  @ColumnDefault(ZERO)
  @Comment("정렬 순서")
  private Integer sort;

  @MapsId("postId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "post_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_POST_ATTACH_POST"))
  @Comment("포스팅 일련 번호")
  private Post post;

  @MapsId("attachId")
  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "attach_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_POST_ATTACH_ATTACH"))
  @Comment("파일 일련 번호")
  private Attach attach;

  private PostAttachMapping(Post post, Attach attach, Integer sort) {
    log.info("PostAttachMapping.Constructor");

    this.id = PostAttachMappingEmbeddedId.createId(post.getId(), attach.getId());
    this.post = post;
    this.attach = attach;
    this.sort = sort;
  }

  public static PostAttachMapping createEntity(Post post, Attach attach, Integer sort) {
    log.info("PostAttachMapping.createEntity");

    return new PostAttachMapping(post, attach, sort);
  }

}
