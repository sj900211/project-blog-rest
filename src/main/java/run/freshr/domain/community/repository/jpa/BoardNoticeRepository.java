package run.freshr.domain.community.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.community.entity.BoardNotice;

public interface BoardNoticeRepository extends JpaRepository<BoardNotice, Long>,
    BoardNoticeRepositoryCustom {

}
