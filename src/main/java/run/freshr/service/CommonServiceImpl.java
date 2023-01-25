package run.freshr.service;

import static java.util.Optional.ofNullable;
import static run.freshr.common.utils.RestUtil.buildId;
import static run.freshr.common.utils.RestUtil.checkProfile;
import static run.freshr.common.utils.RestUtil.ok;
import static run.freshr.utils.MapperUtil.map;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import run.freshr.common.dto.response.IdResponse;
import run.freshr.common.dto.response.IsResponse;
import run.freshr.domain.common.dto.request.AttachCreateRequest;
import run.freshr.domain.common.dto.response.AttachResponse;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.unit.AttachUnit;
import run.freshr.response.PutResultResponse;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonServiceImpl implements CommonService {

  private final AttachUnit attachUnit;

  private final MinioService minioService;

  @Override
  @Transactional
  public ResponseEntity<?> createAttach(AttachCreateRequest dto) throws Exception {
    log.info("CommonService.createAttach");

    List<MultipartFile> files = dto.getFiles();
    List<IdResponse<?>> idList = new ArrayList<>();
    String directory = ofNullable(dto.getDirectory()).orElse(".temp");

    for (MultipartFile file : files) {
      String contentType = ofNullable(file.getContentType()).orElse("");
      String filename = ofNullable(file.getOriginalFilename()).orElse("");

      PutResultResponse uploadResult = minioService.upload(directory, file);

      Long id = attachUnit.create(
          Attach.createEntity(
              contentType,
              filename,
              uploadResult.getPhysical(),
              file.getSize(),
              dto.getAlt(),
              dto.getTitle()
          )
      );

      idList.add(buildId(id));
    }

    return ok(idList);
  }

  @Override
  public ResponseEntity<?> existAttach(Long id) {
    log.info("CommonService.existAttach");

    boolean flag = attachUnit.exists(id);

    return ok(IsResponse.builder().is(flag).build());
  }

  @Override
  public ResponseEntity<?> getAttach(Long id) {
    log.info("CommonService.getAttach");

    Attach entity = attachUnit.get(id);

    return ok(map(entity, AttachResponse.class));
  }

  @Override
  public ResponseEntity<?> getAttachDownload(Long id) throws Exception {
    log.info("CommonService.getAttachDownload");

    if (checkProfile("test")) {
      return ok();
    }

    Attach entity = attachUnit.get(id);

    return minioService.download(entity.getFilename(), entity.getPath());
  }

  @Override
  @Transactional
  public ResponseEntity<?> deleteAttach(Long id) {
    log.info("CommonService.removeAttach");

    Attach entity = attachUnit.get(id);

    entity.removeEntity();

    return ok();
  }

}
