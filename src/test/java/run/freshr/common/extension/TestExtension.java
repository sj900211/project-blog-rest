package run.freshr.common.extension;

import static java.util.Arrays.stream;
import static java.util.List.of;
import static java.util.Objects.isNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.TestRunner.managerMajorId;
import static run.freshr.TestRunner.managerMinorId;
import static run.freshr.TestRunner.staffMajorId;
import static run.freshr.TestRunner.staffMinorId;
import static run.freshr.TestRunner.userId;
import static run.freshr.common.security.TokenProvider.signedId;
import static run.freshr.common.security.TokenProvider.signedRole;
import static run.freshr.domain.auth.enumeration.Role.ROLE_ANONYMOUS;
import static run.freshr.domain.auth.enumeration.Role.ROLE_MANAGER_MAJOR;
import static run.freshr.domain.auth.enumeration.Role.ROLE_MANAGER_MINOR;
import static run.freshr.domain.auth.enumeration.Role.ROLE_STAFF_MAJOR;
import static run.freshr.domain.auth.enumeration.Role.ROLE_STAFF_MINOR;
import static run.freshr.domain.auth.enumeration.Role.ROLE_USER;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import run.freshr.common.extension.request.SearchExtension;
import run.freshr.common.security.TokenProvider;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.service.TestService;

/**
 * 공통 테스트 설정 및 기능을 정의.
 *
 * @author FreshR
 * @apiNote 공통 테스트 설정 및 기능을 정의
 * @since 2023. 1. 13. 오전 11:02:06
 */
@Slf4j
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@TestInstance(PER_CLASS)
public abstract class TestExtension {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  protected TokenProvider provider;
  @Autowired
  protected TestService service;
  @Autowired
  private EntityManager entityManager;
  private MockMvc mockMvc;
  private RestDocumentationResultHandler document;

  private final String SCHEME = "https";
  private final String HOST = "rest.freshr.run";
  private final String DOCS_PATH = "{class-name}/{method-name}";
  private final String POPUP_DOCS_PATH = DOCS_PATH + "/popup";

  @BeforeEach
  public void beforeEach(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    log.info("TestExtension.beforeEach");

    this.document = document(
        DOCS_PATH, // 문서 경로 설정
        preprocessRequest( // Request 설정
            modifyUris()
                .scheme(SCHEME)
                .host(HOST), // 문서에 노출되는 도메인 설정
            prettyPrint() // 정리해서 출력
        ),
        preprocessResponse(prettyPrint()) // Response 설정. 정리해서 출력
    );

    this.mockMvc = MockMvcBuilders // MockMvc 공통 설정. 문서 출력 설정
        .webAppContextSetup(webApplicationContext)
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .apply(documentationConfiguration(restDocumentation))
        .alwaysDo(document)
        .build();
  }

  /**
   * 데이터 반영.
   *
   * @apiNote 지금까지의 영속성 컨텍스트 내용을 DB 에 반영
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:06
   */
  protected void apply() {
    log.info("TestExtension.apply");

    entityManager.flush(); // 영속성 컨텍스트 내용을 데이터베이스에 반영
    entityManager.clear(); // 영속성 컨텍스트 초기화
  }

  /**
   * Request Header 설정.
   *
   * @param mockHttpServletRequestBuilder mock http servlet request builder
   * @return header
   * @apiNote 기본적인 Request Header 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  private MockHttpServletRequestBuilder setHeader(
      MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
    log.info("TestExtension.setHeader");

    return mockHttpServletRequestBuilder
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON);
  }

  /**
   * Request Header 설정.
   *
   * @param mockHttpServletRequestBuilder mock http servlet request builder
   * @return multipart
   * @apiNote multipart/form-data 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  private MockHttpServletRequestBuilder setMultipart(
      MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
    log.info("TestExtension.setMultipart");

    return mockHttpServletRequestBuilder
        .contentType(MULTIPART_FORM_DATA)
        .accept(APPLICATION_JSON);
  }

  /**
   * POST 통신.
   *
   * @param uri           uri
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter 외에 설정한 정보가 없는 통신
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions POST(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.POST");

    return mockMvc.perform(setHeader(post(uri, pathVariables)));
  }

  /**
   * POST 통신.
   *
   * @param uri           uri
   * @param token         token
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Authorization 에 JWT 토큰을 따로 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions POST_TOKEN(String uri, String token, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.POST_TOKEN");

    return mockMvc
        .perform(setHeader(post(uri, pathVariables))
            .header("Authorization", "Bearer " + token));
  }

  /**
   * POST 통신.
   *
   * @param <T>           type parameter
   * @param uri           uri
   * @param content       content
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Request Body 를 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public <T> ResultActions POST_BODY(String uri, T content, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.POST_BODY");

    return mockMvc.perform(
        setHeader(post(uri, pathVariables))
            .content(objectMapper.writeValueAsString(content)));
  }

  /**
   * POST 통신.
   *
   * @param uri               uri
   * @param directory         directory
   * @param mockMultipartFile mock multipart file
   * @param pathVariables     path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Multipart 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions POST_MULTIPART(String uri, String directory,
      MockMultipartFile mockMultipartFile, Object... pathVariables) throws Exception {
    log.info("TestExtension.POST_MULTIPART");

    MockMultipartHttpServletRequestBuilder file = multipart(uri, pathVariables)
        .file(mockMultipartFile);

    if (hasLength(directory)) {
      file.param("directory", directory);
    }

    return mockMvc.perform(setMultipart(file));
  }

  /**
   * GET 통신.
   *
   * @param uri           uri
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter 외에 설정한 정보가 없는 통신
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions GET(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.GET");

    return mockMvc.perform(setHeader(get(uri, pathVariables)));
  }

  /**
   * GET 통신.
   *
   * @param <T>           type parameter
   * @param uri           uri
   * @param search        search
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Request Parameter 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public <T extends SearchExtension> ResultActions GET_PARAM(String uri, T search,
      Object... pathVariables) throws Exception {
    log.info("TestExtension.GET_PARAM");

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(uri, pathVariables);

    if (!isNull(search)) {
      stream(search.getClass().getDeclaredFields()).forEach(field -> {
        try {
          field.setAccessible(true);

          if (!isNull(field.get(search))) {
            if (!field.getType().equals(List.class)) {
              mockHttpServletRequestBuilder.param(field.getName(), field.get(search).toString());
            } else {
              String valueString = field.get(search).toString();

              valueString = valueString.substring(1, valueString.length() - 1);

              List<String> valueList = of(valueString.split(", "));
              int max = valueList.size();

              for (int i = 0; i < max; i++) {
                mockHttpServletRequestBuilder
                    .param(field.getName() + "[" + i + "]", valueList.get(i));
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });

      stream(search.getClass().getSuperclass().getDeclaredFields()).forEach(field -> {
        try {
          field.setAccessible(true);

          if (!isNull(field.get(search))) {
            if (!field.getType().equals(List.class)) {
              mockHttpServletRequestBuilder.param(field.getName(), field.get(search).toString());
            } else {
              String valueString = field.get(search).toString();

              valueString = valueString.substring(1, valueString.length() - 1);

              List<String> valueList = of(valueString.split(", "));
              int max = valueList.size();

              for (int i = 0; i < max; i++) {
                mockHttpServletRequestBuilder
                    .param(field.getName() + "[" + i + "]", valueList.get(i));
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    }

    return mockMvc.perform(setHeader(mockHttpServletRequestBuilder));
  }

  /**
   * PUT 통신.
   *
   * @param uri           uri
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter 외에 설정한 정보가 없는 통신
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions PUT(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.PUT");

    return mockMvc.perform(setHeader(put(uri, pathVariables)));
  }

  /**
   * PUT 통신.
   *
   * @param <T>           type parameter
   * @param uri           uri
   * @param content       content
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Request Body 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public <T> ResultActions PUT_BODY(String uri, T content, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.PUT_BODY");

    return mockMvc.perform(
        setHeader(put(uri, pathVariables)).content(objectMapper.writeValueAsString(content)));
  }

  /**
   * DELETE 통신.
   *
   * @param uri           uri
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter 외에 설정한 정보가 없는 통신
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions DELETE(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.DELETE");

    return mockMvc.perform(setHeader(delete(uri, pathVariables)));
  }

  /**
   * Document 작성.
   *
   * @param snippets snippets
   * @return rest documentation result handler
   * @apiNote 코드를 조금이라도 짧게 만들고 싶어서 만든 기능...⭐
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected RestDocumentationResultHandler docs(Snippet... snippets) {
    log.info("TestExtension.docs");

    return document.document(snippets);
  }

  /**
   * 팝업 문서 설정.
   *
   * @param snippets snippets
   * @return rest documentation result handler
   * @apiNote 팝업 문서 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected RestDocumentationResultHandler docsPopup(Snippet... snippets) {
    log.info("TestExtension.docsPopup");

    this.document = document(
        POPUP_DOCS_PATH, // 문서 경로 설정
        preprocessRequest( // Request 설정
            modifyUris()
                .scheme(SCHEME)
                .host(HOST), // 문서에 노출되는 도메인 설정
            prettyPrint() // 정리해서 출력
        ),
        preprocessResponse(prettyPrint()) // Response 설정. 정리해서 출력
    );

    return document.document(snippets);
  }

  /**
   * 인증 정보 설정.
   *
   * @param id   id
   * @param role role
   * @apiNote 인증 정보 설정
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  private void authentication(Long id, Role role) {
    log.info("TestExtension.authentication");

    removeSigned(); // 로그아웃 처리

    if (!role.equals(ROLE_ANONYMOUS)) { // 게스트 권한이 아닐 경우
      service.createAuth(id, role); // 토큰 발급 및 등록
    }

    signedRole.set(role); // 로그인한 계정 권한 설정
    signedId.set(id); // 로그인한 계정 일련 번호 설정

    SecurityContextHolder // 일회용 로그인 설정
        .getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(
            role.getPrivilege(),
            "{noop}",
            AuthorityUtils.createAuthorityList(role.getKey())
        ));
  }

  /**
   * 인증 정보 생성.
   *
   * @apiNote ROLE_MANAGER_MAJOR 권한으로 인증 정보 생성
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setSignedManagerMajor() {
    log.info("TestExtension.setSignedManagerMajor");

    authentication(managerMajorId, ROLE_MANAGER_MAJOR);
  }

  /**
   * 인증 정보 생성.
   *
   * @apiNote ROLE_MANAGER_MINOR 권한으로 인증 정보 생성
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setSignedManagerMinor() {
    log.info("TestExtension.setSignedManagerMinor");

    authentication(managerMinorId, ROLE_MANAGER_MINOR);
  }

  /**
   * 인증 정보 생성.
   *
   * @apiNote ROLE_STAFF_MAJOR 권한으로 인증 정보 생성
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setSignedStaffMajor() {
    log.info("TestExtension.setSignedStaffMajor");

    authentication(staffMajorId, ROLE_STAFF_MAJOR);
  }

  /**
   * 인증 정보 생성.
   *
   * @apiNote ROLE_STAFF_MINOR 권한으로 인증 정보 생성
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setSignedStaffMinor() {
    log.info("TestExtension.setSignedStaffMinor");

    authentication(staffMinorId, ROLE_STAFF_MINOR);
  }

  /**
   * 인증 정보 생성.
   *
   * @apiNote ROLE_USER 권한으로 인증 정보 생성
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setSignedUser() {
    log.info("TestExtension.setSignedUser");

    authentication(userId, ROLE_USER);
  }

  /**
   * 인증 정보 생성.
   *
   * @apiNote ROLE_ANONYMOUS 권한으로 인증 정보 생성
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setAnonymous() {
    log.info("TestExtension.setAnonymous");

    authentication(0L, ROLE_ANONYMOUS);
  }

  /**
   * 계정 일련 번호 조회.
   *
   * @return signed id
   * @apiNote 통신중인 계정의 일련 번호 조회
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected Long getSignedId() {
    log.info("TestExtension.getSignedId");

    return signedId.get();
  }

  /**
   * 권한 조회.
   *
   * @return signed role
   * @apiNote 통신중인 계정의 권한 조회
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected Role getSignedRole() {
    log.info("TestExtension.getSignedRole");

    return signedRole.get();
  }

  /**
   * 인증 정보 제거.
   *
   * @apiNote 통신중인 인증 정보 제거
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void removeSigned() {
    log.info("TestExtension.removeSigned");

    signedRole.remove();
    signedId.remove();
  }

  /**
   * RSA 정보 생성.
   *
   * @apiNote RSA 정보 생성
   * @author FreshR
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setRsa() {
    log.info("TestExtension.setRsa");

    service.createRsa();
  }

}
