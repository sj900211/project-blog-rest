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
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.mapping.embedded.BlogHashtagMappingEmbeddedId;

@Slf4j
@Entity
@Table(name = "TB_MAP_BLOG_HASHTAG")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "연관 관계 관리 > 블로그 해시태그 관리")
public class BlogHashtagMapping {

  @EmbeddedId
  private BlogHashtagMappingEmbeddedId id;

  @ColumnDefault(ZERO)
  @Comment("정렬 순서")
  private Integer sort;

  @MapsId("blogId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "blog_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_BLOG_HASHTAG_BLOG"))
  @Comment("블로그 일련 번호")
  private Blog blog;

  @MapsId("hashtagId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "hashtag_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_BLOG_HASHTAG_HASHTAG"))
  @Comment("해시태그 일련 번호")
  private Hashtag hashtag;

  private BlogHashtagMapping(Blog blog, Hashtag hashtag, Integer sort) {
    log.info("BlogHashtagMapping.Constructor");

    this.id = BlogHashtagMappingEmbeddedId.createId(blog.getId(), hashtag.getId());
    this.blog = blog;
    this.hashtag = hashtag;
    this.sort = sort;
  }

  public static BlogHashtagMapping createEntity(Blog blog, Hashtag hashtag, Integer sort) {
    log.info("BlogHashtagMapping.createEntity");

    return new BlogHashtagMapping(blog, hashtag, sort);
  }

}
