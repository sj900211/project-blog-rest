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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.mapping.embedded.PostHashtagMappingEmbeddedId;

@Slf4j
@Entity
@Table(name = "TB_MAP_POST_HASHTAG")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "연관 관계 관리 > 포스팅 해시태그 관리")
public class PostHashtagMapping {

  @EmbeddedId
  private PostHashtagMappingEmbeddedId id;

  @ColumnDefault(ZERO)
  @Comment("정렬 순서")
  private Integer sort;

  @MapsId("postId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "post_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_POST_HASHTAG_POST"))
  @Comment("블로그 일련 번호")
  private Post post;

  @MapsId("hashtagId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "hashtag_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_POST_HASHTAG_HASHTAG"))
  @Comment("해시태그 일련 번호")
  private Hashtag hashtag;

  private PostHashtagMapping(Post post, Hashtag hashtag, Integer sort) {
    log.info("PostHashtagMapping.Constructor");

    this.id = PostHashtagMappingEmbeddedId.createId(post.getId(), hashtag.getId());
    this.post = post;
    this.hashtag = hashtag;
    this.sort = sort;
  }

  public static PostHashtagMapping createEntity(Post post, Hashtag hashtag, Integer sort) {
    log.info("PostHashtagMapping.createEntity");

    return new PostHashtagMapping(post, hashtag, sort);
  }

}
