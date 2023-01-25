package run.freshr.service;

import org.springframework.http.ResponseEntity;
import run.freshr.domain.community.dto.request.BoardNoticeCreateRequest;
import run.freshr.domain.community.dto.request.BoardNoticeUpdateRequest;
import run.freshr.domain.community.vo.CommunitySearch;

public interface CommunityService {

  ResponseEntity<?> createBoardNotice(BoardNoticeCreateRequest dto);

  ResponseEntity<?> getBoardNoticePage(CommunitySearch search);

  ResponseEntity<?> getBoardNotice(Long id);

  ResponseEntity<?> updateBoardNotice(Long id, BoardNoticeUpdateRequest dto);

  ResponseEntity<?> removeBoardNotice(Long id);

}
