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
public class PostHashtagMappingEmbeddedId implements Serializable {

  private Long postId;

  private String hashtagId;

  private PostHashtagMappingEmbeddedId(Long postId, String hashtagId) {
    log.info("BoardNoticeAttachMappingEmbeddedId.Constructor");

    this.postId = postId;
    this.hashtagId = hashtagId;
  }

  public static PostHashtagMappingEmbeddedId createId(Long postId, String hashtagId) {
    log.info("BoardNoticeAttachMappingEmbeddedId.createId");

    return new PostHashtagMappingEmbeddedId(postId, hashtagId);
  }

}
