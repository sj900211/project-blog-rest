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
public class PostAttachMappingEmbeddedId implements Serializable {

  private Long postId;

  private Long attachId;

  private PostAttachMappingEmbeddedId(Long postId, Long attachId) {
    log.info("BoardNoticeAttachMappingEmbeddedId.Constructor");

    this.postId = postId;
    this.attachId = attachId;
  }

  public static PostAttachMappingEmbeddedId createId(Long postId, Long attachId) {
    log.info("BoardNoticeAttachMappingEmbeddedId.createId");

    return new PostAttachMappingEmbeddedId(postId, attachId);
  }

}
