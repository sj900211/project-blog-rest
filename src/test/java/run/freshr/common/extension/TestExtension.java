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
 * ?????? ????????? ?????? ??? ????????? ??????.
 *
 * @author FreshR
 * @apiNote ?????? ????????? ?????? ??? ????????? ??????
 * @since 2023. 1. 13. ?????? 11:02:06
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
        DOCS_PATH, // ?????? ?????? ??????
        preprocessRequest( // Request ??????
            modifyUris()
                .scheme(SCHEME)
                .host(HOST), // ????????? ???????????? ????????? ??????
            prettyPrint() // ???????????? ??????
        ),
        preprocessResponse(prettyPrint()) // Response ??????. ???????????? ??????
    );

    this.mockMvc = MockMvcBuilders // MockMvc ?????? ??????. ?????? ?????? ??????
        .webAppContextSetup(webApplicationContext)
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .apply(documentationConfiguration(restDocumentation))
        .alwaysDo(document)
        .build();
  }

  /**
   * ????????? ??????.
   *
   * @apiNote ??????????????? ????????? ???????????? ????????? DB ??? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:06
   */
  protected void apply() {
    log.info("TestExtension.apply");

    entityManager.flush(); // ????????? ???????????? ????????? ????????????????????? ??????
    entityManager.clear(); // ????????? ???????????? ?????????
  }

  /**
   * Request Header ??????.
   *
   * @param mockHttpServletRequestBuilder mock http servlet request builder
   * @return header
   * @apiNote ???????????? Request Header ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  private MockHttpServletRequestBuilder setHeader(
      MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
    log.info("TestExtension.setHeader");

    return mockHttpServletRequestBuilder
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON);
  }

  /**
   * Request Header ??????.
   *
   * @param mockHttpServletRequestBuilder mock http servlet request builder
   * @return multipart
   * @apiNote multipart/form-data ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  private MockHttpServletRequestBuilder setMultipart(
      MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
    log.info("TestExtension.setMultipart");

    return mockHttpServletRequestBuilder
        .contentType(MULTIPART_FORM_DATA)
        .accept(APPLICATION_JSON);
  }

  /**
   * POST ??????.
   *
   * @param uri           uri
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter ?????? ????????? ????????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  public ResultActions POST(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.POST");

    return mockMvc.perform(setHeader(post(uri, pathVariables)));
  }

  /**
   * POST ??????.
   *
   * @param uri           uri
   * @param token         token
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Authorization ??? JWT ????????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  public ResultActions POST_TOKEN(String uri, String token, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.POST_TOKEN");

    return mockMvc
        .perform(setHeader(post(uri, pathVariables))
            .header("Authorization", "Bearer " + token));
  }

  /**
   * POST ??????.
   *
   * @param <T>           type parameter
   * @param uri           uri
   * @param content       content
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Request Body ??? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  public <T> ResultActions POST_BODY(String uri, T content, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.POST_BODY");

    return mockMvc.perform(
        setHeader(post(uri, pathVariables))
            .content(objectMapper.writeValueAsString(content)));
  }

  /**
   * POST ??????.
   *
   * @param uri               uri
   * @param directory         directory
   * @param mockMultipartFile mock multipart file
   * @param pathVariables     path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Multipart ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
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
   * GET ??????.
   *
   * @param uri           uri
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter ?????? ????????? ????????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  public ResultActions GET(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.GET");

    return mockMvc.perform(setHeader(get(uri, pathVariables)));
  }

  /**
   * GET ??????.
   *
   * @param <T>           type parameter
   * @param uri           uri
   * @param search        search
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Request Parameter ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
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
   * PUT ??????.
   *
   * @param uri           uri
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter ?????? ????????? ????????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  public ResultActions PUT(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.PUT");

    return mockMvc.perform(setHeader(put(uri, pathVariables)));
  }

  /**
   * PUT ??????.
   *
   * @param <T>           type parameter
   * @param uri           uri
   * @param content       content
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Request Body ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  public <T> ResultActions PUT_BODY(String uri, T content, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.PUT_BODY");

    return mockMvc.perform(
        setHeader(put(uri, pathVariables)).content(objectMapper.writeValueAsString(content)));
  }

  /**
   * DELETE ??????.
   *
   * @param uri           uri
   * @param pathVariables path variables
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter ?????? ????????? ????????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  public ResultActions DELETE(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.DELETE");

    return mockMvc.perform(setHeader(delete(uri, pathVariables)));
  }

  /**
   * Document ??????.
   *
   * @param snippets snippets
   * @return rest documentation result handler
   * @apiNote ????????? ??????????????? ?????? ????????? ????????? ?????? ??????...???
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected RestDocumentationResultHandler docs(Snippet... snippets) {
    log.info("TestExtension.docs");

    return document.document(snippets);
  }

  /**
   * ?????? ?????? ??????.
   *
   * @param snippets snippets
   * @return rest documentation result handler
   * @apiNote ?????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected RestDocumentationResultHandler docsPopup(Snippet... snippets) {
    log.info("TestExtension.docsPopup");

    this.document = document(
        POPUP_DOCS_PATH, // ?????? ?????? ??????
        preprocessRequest( // Request ??????
            modifyUris()
                .scheme(SCHEME)
                .host(HOST), // ????????? ???????????? ????????? ??????
            prettyPrint() // ???????????? ??????
        ),
        preprocessResponse(prettyPrint()) // Response ??????. ???????????? ??????
    );

    return document.document(snippets);
  }

  /**
   * ?????? ?????? ??????.
   *
   * @param id   id
   * @param role role
   * @apiNote ?????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  private void authentication(Long id, Role role) {
    log.info("TestExtension.authentication");

    removeSigned(); // ???????????? ??????

    if (!role.equals(ROLE_ANONYMOUS)) { // ????????? ????????? ?????? ??????
      service.createAuth(id, role); // ?????? ?????? ??? ??????
    }

    signedRole.set(role); // ???????????? ?????? ?????? ??????
    signedId.set(id); // ???????????? ?????? ?????? ?????? ??????

    SecurityContextHolder // ????????? ????????? ??????
        .getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(
            role.getPrivilege(),
            "{noop}",
            AuthorityUtils.createAuthorityList(role.getKey())
        ));
  }

  /**
   * ?????? ?????? ??????.
   *
   * @apiNote ROLE_MANAGER_MAJOR ???????????? ?????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected void setSignedManagerMajor() {
    log.info("TestExtension.setSignedManagerMajor");

    authentication(managerMajorId, ROLE_MANAGER_MAJOR);
  }

  /**
   * ?????? ?????? ??????.
   *
   * @apiNote ROLE_MANAGER_MINOR ???????????? ?????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected void setSignedManagerMinor() {
    log.info("TestExtension.setSignedManagerMinor");

    authentication(managerMinorId, ROLE_MANAGER_MINOR);
  }

  /**
   * ?????? ?????? ??????.
   *
   * @apiNote ROLE_STAFF_MAJOR ???????????? ?????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected void setSignedStaffMajor() {
    log.info("TestExtension.setSignedStaffMajor");

    authentication(staffMajorId, ROLE_STAFF_MAJOR);
  }

  /**
   * ?????? ?????? ??????.
   *
   * @apiNote ROLE_STAFF_MINOR ???????????? ?????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected void setSignedStaffMinor() {
    log.info("TestExtension.setSignedStaffMinor");

    authentication(staffMinorId, ROLE_STAFF_MINOR);
  }

  /**
   * ?????? ?????? ??????.
   *
   * @apiNote ROLE_USER ???????????? ?????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected void setSignedUser() {
    log.info("TestExtension.setSignedUser");

    authentication(userId, ROLE_USER);
  }

  /**
   * ?????? ?????? ??????.
   *
   * @apiNote ROLE_ANONYMOUS ???????????? ?????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected void setAnonymous() {
    log.info("TestExtension.setAnonymous");

    authentication(0L, ROLE_ANONYMOUS);
  }

  /**
   * ?????? ?????? ?????? ??????.
   *
   * @return signed id
   * @apiNote ???????????? ????????? ?????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected Long getSignedId() {
    log.info("TestExtension.getSignedId");

    return signedId.get();
  }

  /**
   * ?????? ??????.
   *
   * @return signed role
   * @apiNote ???????????? ????????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected Role getSignedRole() {
    log.info("TestExtension.getSignedRole");

    return signedRole.get();
  }

  /**
   * ?????? ?????? ??????.
   *
   * @apiNote ???????????? ?????? ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected void removeSigned() {
    log.info("TestExtension.removeSigned");

    signedRole.remove();
    signedId.remove();
  }

  /**
   * RSA ?????? ??????.
   *
   * @apiNote RSA ?????? ??????
   * @author FreshR
   * @since 2023. 1. 13. ?????? 11:02:07
   */
  protected void setRsa() {
    log.info("TestExtension.setRsa");

    service.createRsa();
  }

}
