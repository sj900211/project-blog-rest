package run.freshr.controller;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.formParameters;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static run.freshr.TestRunner.attachIdList;
import static run.freshr.common.config.URIConfig.uriCommonAttach;
import static run.freshr.common.config.URIConfig.uriCommonAttachExist;
import static run.freshr.common.config.URIConfig.uriCommonAttachId;
import static run.freshr.common.config.URIConfig.uriCommonAttachIdDownload;
import static run.freshr.common.config.URIConfig.uriCommonEnum;
import static run.freshr.common.config.URIConfig.uriCommonEnumPick;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import run.freshr.annotation.Docs;
import run.freshr.annotation.DocsGroup;
import run.freshr.common.extension.TestExtension;
import run.freshr.domain.auth.enumeration.Privilege;
import run.freshr.domain.common.AttachDocs;
import run.freshr.domain.common.EnumDocs;

@Slf4j
@DisplayName("공통 관리")
@DocsGroup(name = "common")
class CommonControllerTest extends TestExtension {

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|

  @Test
  @DisplayName("열거형 Data 조회 - All")
  @Docs
  public void getEnumList() throws Exception {
    log.info("CommonControllerTest.getEnumList");

    setAnonymous();

    GET(uriCommonEnum)
        .andDo(print())
        .andDo(docsPopup(EnumDocs.Response.getEnumList(service.getEnumAll())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("열거형 Data 조회 - One To Many")
  @Docs(existsPathParameters = true)
  public void getEnum() throws Exception {
    log.info("CommonControllerTest.getEnum");

    setAnonymous();

    GET(uriCommonEnumPick,
        UPPER_CAMEL.to(LOWER_HYPHEN, Privilege.class.getSimpleName()).toLowerCase())
        .andDo(print())
        .andDo(docs(pathParameters(EnumDocs.Request.getEnum())))
        .andExpect(status().isOk());
  }

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|

  @Test
  @DisplayName("파일 업로드")
  @Docs(existsRequestParts = true, existsFormParameters = true, existsResponseFields = true)
  public void createAttach() throws Exception {
    log.info("CommonControllerTest.createAttach");

    setSignedManagerMajor();

    apply();

    POST_MULTIPART(
        uriCommonAttach,
        "",
        new MockMultipartFile("files", "original", IMAGE_PNG_VALUE,
            new byte[]{1})
    ).andDo(print())
        .andDo(docs(
            requestParts(AttachDocs.Request.createAttachFile()),
            formParameters(AttachDocs.Request.createAttach()),
            responseFields(AttachDocs.Response.createAttach())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 존재 여부 확인")
  @Docs(existsPathParameters = true, existsResponseFields = true)
  public void existAttach() throws Exception {
    log.info("CommonControllerTest.existAttach");

    setSignedManagerMajor();

    apply();

    GET(uriCommonAttachExist, attachIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(AttachDocs.Request.existAttach()),
            responseFields(AttachDocs.Response.existAttach())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 상세 조회")
  @Docs(existsPathParameters = true, existsResponseFields = true)
  public void getAttach() throws Exception {
    log.info("CommonControllerTest.getAttach");

    setSignedManagerMajor();

    apply();

    GET(uriCommonAttachId, attachIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(AttachDocs.Request.getAttach()),
            responseFields(AttachDocs.Response.getAttach())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 다운로드")
  @Docs(existsPathParameters = true)
  public void getAttachDownload() throws Exception {
    log.info("CommonControllerTest.getAttachDownload");

    setSignedManagerMajor();

    apply();

    GET(uriCommonAttachIdDownload, attachIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(AttachDocs.Request.getAttach())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 삭제")
  @Docs(existsPathParameters = true)
  public void removeAttach() throws Exception {
    log.info("CommonControllerTest.removeAttach");

    setSignedManagerMajor();

    apply();

    DELETE(uriCommonAttachId, attachIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(AttachDocs.Request.removeAttach())))
        .andExpect(status().isOk());
  }

}