package run.freshr.domain.mapping.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.community.entity.BoardNotice;
import run.freshr.domain.mapping.embedded.BoardNoticeAttachMappingEmbeddedId;
import run.freshr.domain.mapping.entity.BoardNoticeAttachMapping;

public interface BoardNoticeAttachMappingRepository extends
    JpaRepository<BoardNoticeAttachMapping, BoardNoticeAttachMappingEmbeddedId> {

  boolean existsByIdNoticeIdAndIdAttachId(Long noticeId, Long attachId);

  Optional<BoardNoticeAttachMapping> findByIdNoticeIdAndIdAttachId(Long noticeId, Long attachId);

  void deleteAllByNotice(BoardNotice entity);

}
