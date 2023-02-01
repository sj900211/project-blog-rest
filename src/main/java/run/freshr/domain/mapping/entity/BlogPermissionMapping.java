package run.freshr.domain.mapping.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
import run.freshr.domain.blog.enumeration.BlogRole;
import run.freshr.domain.mapping.embedded.BlogPermissionMappingEmbeddedId;

@Slf4j
@Entity
@Table(name = "TB_MAP_BLOG_ROLE")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment(value = "연관 관계 관리 > 블로그 권한 관리")
public class BlogPermissionMapping {

  @EmbeddedId
  private BlogPermissionMappingEmbeddedId id;

  @MapsId("blogId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "blog_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_BLOG_ROLE_BLOG"))
  @Comment("블로그 일련 번호")
  private Blog blog;

  @MapsId("accountId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "account_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_MAP_BLOG_ROLE_SIGN"))
  @Comment("계정 일련 번호")
  private Account account;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("권한")
  private BlogRole role;

  private BlogPermissionMapping(Blog blog, Account account, BlogRole role) {
    log.info("BlogPermissionMapping.Constructor");

    this.id = BlogPermissionMappingEmbeddedId.createId(blog.getId(), account.getId());
    this.blog = blog;
    this.account = account;
    this.role = role;
  }

  public static BlogPermissionMapping createEntity(Blog blog, Account account, BlogRole role) {
    log.info("BlogPermissionMapping.createEntity");

    return new BlogPermissionMapping(blog, account, role);
  }

  public void updateEntity(BlogRole role) {
    log.info("BlogPermissionMapping.updateEntity");

    this.role = role;
  }

}
