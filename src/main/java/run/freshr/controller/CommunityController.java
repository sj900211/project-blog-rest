package run.freshr.controller;

import static run.freshr.common.config.URIConfig.uriCommunityNotice;
import static run.freshr.common.config.URIConfig.uriCommunityNoticeId;
import static run.freshr.common.utils.RestUtil.error;
import static run.freshr.domain.auth.enumeration.Role.Secured.ANONYMOUS;
import static run.freshr.domain.auth.enumeration.Role.Secured.MANAGER_MAJOR;
import static run.freshr.domain.auth.enumeration.Role.Secured.MANAGER_MINOR;
import static run.freshr.domain.auth.enumeration.Role.Secured.USER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.freshr.domain.community.dto.request.BoardNoticeCreateRequest;
import run.freshr.domain.community.dto.request.BoardNoticeUpdateRequest;
import run.freshr.domain.community.validator.CommunityValidator;
import run.freshr.domain.community.vo.CommunitySearch;
import run.freshr.service.CommunityService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommunityController {

  private final CommunityService service;
  private final CommunityValidator validator;

  @Secured({MANAGER_MAJOR, MANAGER_MINOR})
  @PostMapping(uriCommunityNotice)
  public ResponseEntity<?> createBoard(@RequestBody @Valid BoardNoticeCreateRequest dto) {
    log.info("CommunityController.createBoard");

    return service.createBoardNotice(dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriCommunityNotice)
  public ResponseEntity<?> getBoardPage(@ModelAttribute @Valid CommunitySearch search,
      Errors errors) {
    log.info("CommunityController.getBoardPage");

    validator.getBoardPage(search, errors);

    if (errors.hasErrors()) {
      return error(errors);
    }

    return service.getBoardNoticePage(search);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriCommunityNoticeId)
  public ResponseEntity<?> getBoard(@PathVariable Long id) {
    log.info("CommunityController.getBoard");

    return service.getBoardNotice(id);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR})
  @PutMapping(uriCommunityNoticeId)
  public ResponseEntity<?> updateBoard(@PathVariable Long id,
      @RequestBody @Valid BoardNoticeUpdateRequest dto) {
    log.info("CommunityController.updateBoard");

    return service.updateBoardNotice(id, dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR})
  @DeleteMapping(uriCommunityNoticeId)
  public ResponseEntity<?> removeBoard(@PathVariable Long id) {
    log.info("CommunityController.removeBoard");

    return service.removeBoardNotice(id);
  }

}
