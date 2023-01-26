package run.freshr.domain.mapping.embedded;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class BlogRoleMappingEmbeddedId implements Serializable {

  private Long blogId;

  private Long accountId;

  private BlogRoleMappingEmbeddedId(Long blogId, Long accountId) {
    log.info("BlogRoleMappingEmbeddedId.Constructor");

    this.blogId = blogId;
    this.accountId = accountId;
  }

  public static BlogRoleMappingEmbeddedId createId(Long blogId, Long accountId) {
    log.info("BlogRoleMappingEmbeddedId.createId");

    return new BlogRoleMappingEmbeddedId(blogId, accountId);
  }

}
