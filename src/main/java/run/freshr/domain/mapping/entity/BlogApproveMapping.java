package run.freshr.domain.mapping.entity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

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
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.mapping.embedded.BlogApproveMappingEmbeddedId;

@Slf4j
@Entity
@Table(name = "TB_MAP_BLOG_APPROVE")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "연관 관계 관리 > 블로그 접근 허용 관리")
public class BlogApproveMapping {

  @EmbeddedId
  private BlogApproveMappingEmbeddedId id;

  @MapsId("blogId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "blog_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_BLOG_APPROVE_BLOG"))
  @Comment("블로그 일련 번호")
  private Blog blog;

  @MapsId("accountId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "account_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_BLOG_APPROVE_SIGN"))
  @Comment("계정 일련 번호")
  private Account account;

  private BlogApproveMapping(Blog blog, Account account) {
    log.info("BlogApproveMapping.Constructor");

    this.id = BlogApproveMappingEmbeddedId.createId(blog.getId(), account.getId());
    this.blog = blog;
    this.account = account;
  }

  public static BlogApproveMapping createEntity(Blog blog, Account account) {
    log.info("BlogApproveMapping.createEntity");

    return new BlogApproveMapping(blog, account);
  }

}
