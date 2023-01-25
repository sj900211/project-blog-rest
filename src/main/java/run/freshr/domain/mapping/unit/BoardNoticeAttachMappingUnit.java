package run.freshr.domain.mapping.unit;

import run.freshr.common.extension.unit.UnitDefaultExtension;
import run.freshr.domain.community.entity.BoardNotice;
import run.freshr.domain.mapping.embedded.BoardNoticeAttachMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BoardNoticeAttachMapping;

public interface BoardNoticeAttachMappingUnit extends
    UnitDefaultExtension<BoardNoticeAttachMapping, BoardNoticeAttachMappingEmbeddedId> {

  void delete(BoardNotice entity);

}
