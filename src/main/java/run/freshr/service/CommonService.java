package run.freshr.service;

import org.springframework.http.ResponseEntity;
import run.freshr.domain.common.dto.request.AttachCreateRequest;
import run.freshr.domain.common.vo.CommonSearch;

public interface CommonService {

  ResponseEntity<?> createAttach(AttachCreateRequest dto) throws Exception;

  ResponseEntity<?> existAttach(Long id);

  ResponseEntity<?> getAttach(Long id);

  ResponseEntity<?> getAttachDownload(Long id) throws Exception;

  ResponseEntity<?> deleteAttach(Long id);

  ResponseEntity<?> searchHashtag(String keyword);

  ResponseEntity<?> getHashtagStatistics();

  ResponseEntity<?> getBlogPageByHashtag(CommonSearch search);

  ResponseEntity<?> getPostPageByHashtag(CommonSearch search);

}
