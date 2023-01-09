package run.freshr.service;

import org.springframework.http.ResponseEntity;
import run.freshr.domain.common.dto.request.AttachCreateRequest;

public interface CommonService {

  ResponseEntity<?> createAttach(AttachCreateRequest dto) throws Exception;

  ResponseEntity<?> existAttach(Long id);

  ResponseEntity<?> getAttach(Long id);

  ResponseEntity<?> getAttachDownload(Long id) throws Exception;

  ResponseEntity<?> deleteAttach(Long id);

}
