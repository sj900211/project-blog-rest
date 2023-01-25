package run.freshr.domain.community.repository.jpa;

import org.springframework.data.domain.Page;
import run.freshr.domain.community.entity.BoardNotice;
import run.freshr.domain.community.vo.CommunitySearch;

public interface BoardNoticeRepositoryCustom {

  Page<BoardNotice> getPage(CommunitySearch search);

}
