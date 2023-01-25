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
public class BoardNoticeAttachMappingEmbeddedId implements Serializable {

  private Long noticeId;

  private Long attachId;

  private BoardNoticeAttachMappingEmbeddedId(Long noticeId, Long attachId) {
    log.info("BoardNoticeAttachMappingEmbeddedId.Constructor");

    this.noticeId = noticeId;
    this.attachId = attachId;
  }

  public static BoardNoticeAttachMappingEmbeddedId createId(Long noticeId, Long attachId) {
    log.info("BoardNoticeAttachMappingEmbeddedId.createId");

    return new BoardNoticeAttachMappingEmbeddedId(noticeId, attachId);
  }

}
