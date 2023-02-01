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
public class BlogPermissionMappingEmbeddedId implements Serializable {

  private Long blogId;

  private Long accountId;

  private BlogPermissionMappingEmbeddedId(Long blogId, Long accountId) {
    log.info("BlogPermissionMappingEmbeddedId.Constructor");

    this.blogId = blogId;
    this.accountId = accountId;
  }

  public static BlogPermissionMappingEmbeddedId createId(Long blogId, Long accountId) {
    log.info("BlogPermissionMappingEmbeddedId.createId");

    return new BlogPermissionMappingEmbeddedId(blogId, accountId);
  }

}
