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
public class BlogRequestMappingEmbeddedId implements Serializable {

  private Long blogId;

  private Long accountId;

  private BlogRequestMappingEmbeddedId(Long blogId, Long accountId) {
    log.info("BlogRequestMappingEmbeddedId.Constructor");

    this.blogId = blogId;
    this.accountId = accountId;
  }

  public static BlogRequestMappingEmbeddedId createId(Long blogId, Long accountId) {
    log.info("BlogRequestMappingEmbeddedId.createId");

    return new BlogRequestMappingEmbeddedId(blogId, accountId);
  }

}
