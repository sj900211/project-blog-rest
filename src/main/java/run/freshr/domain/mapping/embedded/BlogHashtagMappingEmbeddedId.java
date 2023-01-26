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
public class BlogHashtagMappingEmbeddedId implements Serializable {

  private Long blogId;

  private String hashtagId;

  private BlogHashtagMappingEmbeddedId(Long blogId, String hashtagId) {
    log.info("BoardNoticeAttachMappingEmbeddedId.Constructor");

    this.blogId = blogId;
    this.hashtagId = hashtagId;
  }

  public static BlogHashtagMappingEmbeddedId createId(Long blogId, String hashtagId) {
    log.info("BoardNoticeAttachMappingEmbeddedId.createId");

    return new BlogHashtagMappingEmbeddedId(blogId, hashtagId);
  }

}
