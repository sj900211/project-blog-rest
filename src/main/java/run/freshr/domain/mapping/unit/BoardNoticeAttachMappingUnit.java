package run.freshr.domain.mapping.unit;

import run.freshr.common.extension.unit.UnitDefaultExtension;
import run.freshr.domain.community.entity.BoardNotice;
import run.freshr.domain.mapping.embedded.BoardNoticeAttachMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BoardNoticeAttachMapping;

public interface BoardNoticeAttachMappingUnit extends
    UnitDefaultExtension<BoardNoticeAttachMapping, BoardNoticeAttachMappingEmbeddedId> {

  Boolean exists(Long noticeId, Long attachId);

  BoardNoticeAttachMapping get(Long noticeId, Long attachId);

  void delete(BoardNotice entity);

}
