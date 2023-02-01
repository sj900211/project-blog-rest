package run.freshr.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static run.freshr.TestRunner.attachIdList;
import static run.freshr.TestRunner.noticeIdList;
import static run.freshr.common.config.URIConfig.uriCommunityNotice;
import static run.freshr.common.config.URIConfig.uriCommunityNoticeId;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import run.freshr.annotation.Docs;
import run.freshr.annotation.DocsGroup;
import run.freshr.annotation.DocsPopup;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.common.extension.TestExtension;
import run.freshr.domain.community.BoardNoticeDocs;
import run.freshr.domain.community.BoardNoticeDocs.Request;
import run.freshr.domain.community.BoardNoticeDocs.Response;
import run.freshr.domain.community.dto.request.BoardNoticeCreateRequest;
import run.freshr.domain.community.dto.request.BoardNoticeUpdateRequest;
import run.freshr.domain.community.enumeration.BoardNoticeExpose;
import run.freshr.domain.community.vo.CommunitySearch;
import run.freshr.domain.mapping.dto.request.BoardNoticeAttachMappingSaveRequest;

@Slf4j
@DocsGroup(name = "community")
@DisplayName("커뮤니티 관리")
class CommunityControllerTest extends TestExtension {

  @Test
  @DisplayName("공지사항 등록")
  @Docs(existsRequestFields = true, existsResponseFields = true)
  public void createBoardNotice() throws Exception {
    setSignedManagerMajor();

    apply();

    POST_BODY(uriCommunityNotice,
        BoardNoticeCreateRequest
            .builder()
            .title("input title")
            .contents("input contents")
            .fixed(false)
            .expose(BoardNoticeExpose.ALL)
            .attachList(List.of(
                BoardNoticeAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(0))
                        .build())
                    .sort(0)
                    .build(),
                BoardNoticeAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(1))
                        .build())
                    .sort(1)
                    .build(),
                BoardNoticeAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(2))
                        .build())
                    .sort(2)
                    .build()
            )).build())
        .andDo(print())
        .andDo(docs(requestFields(Request.createBoardNotice()),
            responseFields(Response.createBoardNotice())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("공지사항 조회 - Page")
  @Docs(existsQueryParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "board-notice-docs-get-board-notice-page-key",
          include = "common-controller-test/get-enum-list/popup/popup-fields-board-notice-search-keys.adoc")
  })
  public void getBoardNoticePage() throws Exception {
    setAnonymous();

    apply();

    CommunitySearch search = new CommunitySearch();

    search.setPage(2);
    search.setSize(5);

    GET_PARAM(uriCommunityNotice, search)
        .andDo(print())
        .andDo(docs(
            queryParameters(BoardNoticeDocs.Request.getBoardNoticePage()),
            responseFields(BoardNoticeDocs.Response.getBoardNoticePage())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("공지사항 조회")
  @Docs(existsPathParameters = true, existsResponseFields = true)
  public void getBoardNotice() throws Exception {
    setAnonymous();

    apply();

    GET(uriCommunityNoticeId, noticeIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(BoardNoticeDocs.Request.getBoardNotice()),
            responseFields(BoardNoticeDocs.Response.getBoardNotice())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("공지사항 수정")
  @Docs(existsPathParameters = true, existsRequestFields = true)
  public void updateBoardNotice() throws Exception {
    setSignedManagerMajor();

    apply();

    PUT_BODY(uriCommunityNoticeId,
        BoardNoticeUpdateRequest
            .builder()
            .title("input title")
            .contents("input contents")
            .fixed(false)
            .expose(BoardNoticeExpose.ALL)
            .attachList(List.of(
                BoardNoticeAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(0))
                        .build())
                    .sort(0)
                    .build(),
                BoardNoticeAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(1))
                        .build())
                    .sort(1)
                    .build(),
                BoardNoticeAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(2))
                        .build())
                    .sort(2)
                    .build()
            )).build(),
        noticeIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(BoardNoticeDocs.Request.updateBoardNoticePath()),
            requestFields(BoardNoticeDocs.Request.updateBoardNotice())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("공지사항 삭제")
  @Docs(existsPathParameters = true)
  public void removeBoardNotice() throws Exception {
    setSignedManagerMajor();

    apply();

    DELETE(uriCommunityNoticeId, noticeIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(BoardNoticeDocs.Request.removeBoardNotice())))
        .andExpect(status().isOk());
  }

}
