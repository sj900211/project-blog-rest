package run.freshr.service;

import static java.util.Optional.ofNullable;
import static run.freshr.common.utils.RestUtil.buildId;
import static run.freshr.common.utils.RestUtil.error;
import static run.freshr.common.utils.RestUtil.getExceptions;
import static run.freshr.common.utils.RestUtil.ok;
import static run.freshr.utils.MapperUtil.map;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.unit.AttachUnit;
import run.freshr.domain.community.dto.request.BoardNoticeCreateRequest;
import run.freshr.domain.community.dto.request.BoardNoticeUpdateRequest;
import run.freshr.domain.community.dto.response.BoardNoticeListResponse;
import run.freshr.domain.community.dto.response.BoardNoticeResponse;
import run.freshr.domain.community.entity.BoardNotice;
import run.freshr.domain.community.unit.BoardNoticeUnit;
import run.freshr.domain.community.vo.CommunitySearch;
import run.freshr.domain.mapping.entity.BoardNoticeAttachMapping;
import run.freshr.domain.mapping.unit.BoardNoticeAttachMappingUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityServiceImpl implements CommunityService {

  private final BoardNoticeUnit boardNoticeUnit;
  private final AttachUnit attachUnit;
  private final BoardNoticeAttachMappingUnit boardAttachMappingUnit;

  @Override
  @Transactional
  public ResponseEntity<?> createBoardNotice(BoardNoticeCreateRequest dto) {
    log.info("CommunityService.createBoardNotice");

    BoardNotice entity = BoardNotice.createEntity(dto.getTitle(), dto.getContents(),
        dto.getFixed(), dto.getExpose());
    Long id = boardNoticeUnit.create(entity);

    ofNullable(dto.getAttachList()).orElse(new ArrayList<>()).forEach(item -> {
      Attach attach = attachUnit.get(item.getAttach().getId());

      boardAttachMappingUnit
          .create(BoardNoticeAttachMapping.createEntity(entity, attach, item.getSort()));
    });

    return ok(buildId(id));
  }

  @Override
  public ResponseEntity<?> getBoardNoticePage(CommunitySearch search) {
    log.info("CommunityService.getBoardNoticePage");

    Page<BoardNotice> entityPage = boardNoticeUnit.getPage(search);
    Page<BoardNoticeListResponse> page = entityPage.map(
        item -> map(item, BoardNoticeListResponse.class));

    return ok(page);
  }

  @Override
  @Transactional
  public ResponseEntity<?> getBoardNotice(Long id) {
    log.info("CommunityService.getBoardNotice");

    if (!boardNoticeUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    BoardNotice entity = boardNoticeUnit.get(id);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    entity.addViews();

    BoardNoticeResponse data = map(entity, BoardNoticeResponse.class);

    return ok(data);
  }

  @Override
  @Transactional
  public ResponseEntity<?> updateBoardNotice(Long id, BoardNoticeUpdateRequest dto) {
    log.info("CommunityService.updateBoardNotice");

    if (!boardNoticeUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    BoardNotice entity = boardNoticeUnit.get(id);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    entity.updateEntity(dto.getTitle(), dto.getContents(), dto.getFixed(), dto.getExpose());

    boardAttachMappingUnit.delete(entity);

    ofNullable(dto.getAttachList()).orElse(new ArrayList<>()).forEach(item -> {
      Attach attach = attachUnit.get(item.getAttach().getId());

      boardAttachMappingUnit
          .create(BoardNoticeAttachMapping.createEntity(entity, attach, item.getSort()));
    });

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> removeBoardNotice(Long id) {
    log.info("CommunityService.removeBoardNotice");

    if (!boardNoticeUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    BoardNotice entity = boardNoticeUnit.get(id);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    entity.removeEntity();

    return ok();
  }

}
