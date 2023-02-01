package run.freshr.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static run.freshr.TestRunner.attachIdList;
import static run.freshr.TestRunner.blogIdList;
import static run.freshr.TestRunner.hashtagList;
import static run.freshr.TestRunner.postIdList;
import static run.freshr.TestRunner.userId;
import static run.freshr.TestRunner.userIdList;
import static run.freshr.common.config.URIConfig.uriBlog;
import static run.freshr.common.config.URIConfig.uriBlogId;
import static run.freshr.common.config.URIConfig.uriBlogIdApproval;
import static run.freshr.common.config.URIConfig.uriBlogIdHasPermission;
import static run.freshr.common.config.URIConfig.uriBlogIdPermit;
import static run.freshr.common.config.URIConfig.uriBlogIdRequest;
import static run.freshr.common.config.URIConfig.uriBlogPost;
import static run.freshr.common.config.URIConfig.uriBlogPostId;
import static run.freshr.common.config.URIConfig.uriBlogPostIdHasPermission;
import static run.freshr.domain.blog.enumeration.BlogRole.REPORTER;
import static run.freshr.domain.blog.enumeration.BlogViewType.PUBLIC;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import run.freshr.annotation.Docs;
import run.freshr.annotation.DocsGroup;
import run.freshr.annotation.DocsPopup;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.common.extension.TestExtension;
import run.freshr.domain.blog.BlogDocs;
import run.freshr.domain.blog.PostDocs;
import run.freshr.domain.blog.dto.request.BlogCreateRequest;
import run.freshr.domain.blog.dto.request.BlogUpdateRequest;
import run.freshr.domain.blog.dto.request.PostCreateRequest;
import run.freshr.domain.blog.dto.request.PostUpdateRequest;
import run.freshr.domain.community.vo.CommunitySearch;
import run.freshr.domain.mapping.dto.request.BlogHashtagMappingSaveRequest;
import run.freshr.domain.mapping.dto.request.BlogPermissionMappingApprovalRequest;
import run.freshr.domain.mapping.dto.request.BlogPermissionMappingSaveRequest;
import run.freshr.domain.mapping.dto.request.PostAttachMappingSaveRequest;
import run.freshr.domain.mapping.dto.request.PostHashtagMappingSaveRequest;

@Slf4j
@DisplayName("블로그 관리")
@DocsGroup(name = "blog")
public class BlogControllerTest extends TestExtension {

  // .______    __        ______     _______
  // |   _  \  |  |      /  __  \   /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |
  // |______/  |_______| \______/   \______|

  @Test
  @DisplayName("블로그 등록")
  @Docs(existsRequestFields = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "blog-docs-create-blog-view-type",
          include = "common-controller-test/get-enum-list/popup/popup-fields-blog-view-type.adoc")
  })
  public void createBlog() throws Exception {
    setSignedManagerMajor();

    apply();

    POST_BODY(uriBlog,
        BlogCreateRequest
            .builder()
            .viewType(PUBLIC)
            .title("input title")
            .description("input description")
            .cover(IdRequest.<Long>builder().id(attachIdList.get(0)).build())
            .hashtagList(List.of(
                BlogHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(0))
                        .build())
                    .sort(0)
                    .build(),
                BlogHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(1))
                        .build())
                    .sort(1)
                    .build(),
                BlogHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(2))
                        .build())
                    .sort(2)
                    .build()
            )).build())
        .andDo(print())
        .andDo(docs(requestFields(BlogDocs.Request.createBlog()),
            responseFields(BlogDocs.Response.createBlog())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("블로그 조회 - Page")
  @Docs(existsQueryParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "blog-docs-get-blog-page-key",
          include = "common-controller-test/get-enum-list/popup/popup-fields-blog-search-keys.adoc"),
      @DocsPopup(name = "blog-docs-get-blog-page-view-type",
          include = "common-controller-test/get-enum-list/popup/popup-fields-blog-view-type.adoc")
  })
  public void getBlogPage() throws Exception {
    setSignedManagerMajor();

    apply();

    CommunitySearch search = new CommunitySearch();

    search.setPage(2);
    search.setSize(5);

    GET_PARAM(uriBlog, search)
        .andDo(print())
        .andDo(docs(
            queryParameters(BlogDocs.Request.getBlogPage()),
            responseFields(BlogDocs.Response.getBlogPage())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("블로그 조회")
  @Docs(existsPathParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "blog-docs-get-blog-view-type",
          include = "common-controller-test/get-enum-list/popup/popup-fields-blog-view-type.adoc")
  })
  public void getBlog() throws Exception {
    setSignedManagerMajor();

    apply();

    GET(uriBlogId, blogIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(BlogDocs.Request.getBlog()),
            responseFields(BlogDocs.Response.getBlog())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("블로그 수정")
  @Docs(existsPathParameters = true, existsRequestFields = true, popup = {
      @DocsPopup(name = "blog-docs-update-blog-view-type",
          include = "common-controller-test/get-enum-list/popup/popup-fields-blog-view-type.adoc")
  })
  public void updateBlog() throws Exception {
    setSignedManagerMajor();

    apply();

    PUT_BODY(uriBlogId,
        BlogUpdateRequest
            .builder()
            .viewType(PUBLIC)
            .title("input title")
            .description("input description")
            .cover(IdRequest.<Long>builder().id(attachIdList.get(0)).build())
            .hashtagList(List.of(
                BlogHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(0))
                        .build())
                    .sort(0)
                    .build(),
                BlogHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(1))
                        .build())
                    .sort(1)
                    .build(),
                BlogHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(2))
                        .build())
                    .sort(2)
                    .build()
            )).build(),
        blogIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(BlogDocs.Request.updateBlogPath()),
            requestFields(BlogDocs.Request.updateBlog())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("블로그 삭제")
  @Docs(existsPathParameters = true)
  public void removeBlog() throws Exception {
    setSignedManagerMajor();

    apply();

    DELETE(uriBlogId, blogIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(BlogDocs.Request.removeBlog())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("블로그 권한 요청")
  @Docs(existsPathParameters = true)
  public void requestBlog() throws Exception {
    setSignedManagerMajor();

    apply();

    POST(uriBlogIdRequest, blogIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(BlogDocs.Request.requestBlog())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("블로그 권한 승인")
  @Docs(existsPathParameters = true, existsRequestFields = true)
  public void approvalBlog() throws Exception {
    setSignedManagerMajor();

    apply();

    POST_BODY(uriBlogIdApproval,
        BlogPermissionMappingApprovalRequest
            .builder()
            .account(IdRequest.<Long>builder().id(userIdList.get(0)).build())
            .build(), blogIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(BlogDocs.Request.approvalBlogPath()),
            requestFields(BlogDocs.Request.approvalBlog())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("블로그 권한 수정")
  @Docs(existsPathParameters = true, existsRequestFields = true, popup = {
      @DocsPopup(name = "blog-docs-permit-blog-role",
          include = "common-controller-test/get-enum-list/popup/popup-fields-blog-role.adoc")
  })
  public void permitBlog() throws Exception {
    setSignedManagerMajor();

    apply();

    PUT_BODY(uriBlogIdPermit,
        BlogPermissionMappingSaveRequest
            .builder()
            .account(IdRequest.<Long>builder().id(userId).build())
            .role(REPORTER)
            .build(), blogIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(BlogDocs.Request.permitBlogPath()),
            requestFields(BlogDocs.Request.permitBlog())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("블로그 권한 조회")
  @Docs(existsPathParameters = true)
  public void hasPermissionForBlog() throws Exception {
    setSignedManagerMajor();

    apply();

    GET(uriBlogIdHasPermission, blogIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(BlogDocs.Request.hasPermissionForBlog())))
        .andExpect(status().isOk());
  }

  // .______     ______        _______.___________.
  // |   _  \   /  __  \      /       |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |
  // |  |      |  `--'  | .----)   |      |  |
  // | _|       \______/  |_______/       |__|

  @Test
  @DisplayName("포스팅 등록")
  @Docs(existsPathParameters = true, existsRequestFields = true, existsResponseFields = true)
  public void createPost() throws Exception {
    setSignedManagerMajor();

    apply();

    POST_BODY(uriBlogPost,
        PostCreateRequest
            .builder()
            .title("input title")
            .contents("input description")
            .useFlag(true)
            .hashtagList(List.of(
                PostHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(0))
                        .build())
                    .sort(0)
                    .build(),
                PostHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(1))
                        .build())
                    .sort(1)
                    .build(),
                PostHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(2))
                        .build())
                    .sort(2)
                    .build()
            )).attachList(List.of(
                PostAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(0))
                        .build())
                    .sort(0)
                    .build(),
                PostAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(1))
                        .build())
                    .sort(1)
                    .build(),
                PostAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(2))
                        .build())
                    .sort(2)
                    .build()
            )).build(), blogIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(PostDocs.Request.createPostPath()),
            requestFields(PostDocs.Request.createPost()),
            responseFields(PostDocs.Response.createPost())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("포스팅 조회 - Page")
  @Docs(existsPathParameters = true, existsQueryParameters = true, existsResponseFields = true)
  public void getPostPage() throws Exception {
    setSignedManagerMajor();

    apply();

    CommunitySearch search = new CommunitySearch();

    search.setPage(2);
    search.setSize(5);

    GET_PARAM(uriBlogPost, search, blogIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(PostDocs.Request.getPostPagePath()),
            queryParameters(PostDocs.Request.getPostPage()),
            responseFields(PostDocs.Response.getPostPage())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("포스팅 조회")
  @Docs(existsPathParameters = true, existsResponseFields = true)
  public void getPost() throws Exception {
    setSignedManagerMajor();

    apply();

    GET(uriBlogPostId, blogIdList.get(0), postIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(PostDocs.Request.getPost()),
            responseFields(PostDocs.Response.getPost())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("포스팅 수정")
  @Docs(existsPathParameters = true, existsRequestFields = true)
  public void updatePost() throws Exception {
    setSignedManagerMajor();

    apply();

    PUT_BODY(uriBlogPostId,
        PostUpdateRequest
            .builder()
            .title("input title")
            .contents("input description")
            .useFlag(true)
            .hashtagList(List.of(
                PostHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(0))
                        .build())
                    .sort(0)
                    .build(),
                PostHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(1))
                        .build())
                    .sort(1)
                    .build(),
                PostHashtagMappingSaveRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder()
                        .id(hashtagList.get(2))
                        .build())
                    .sort(2)
                    .build()
            )).attachList(List.of(
                PostAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(0))
                        .build())
                    .sort(0)
                    .build(),
                PostAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(1))
                        .build())
                    .sort(1)
                    .build(),
                PostAttachMappingSaveRequest
                    .builder()
                    .attach(IdRequest.<Long>builder()
                        .id(attachIdList.get(2))
                        .build())
                    .sort(2)
                    .build()
            )).build(),
        blogIdList.get(0), postIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(PostDocs.Request.updatePostPath()),
            requestFields(PostDocs.Request.updatePost())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("포스팅 삭제")
  @Docs(existsPathParameters = true)
  public void removePost() throws Exception {
    setSignedManagerMajor();

    apply();

    DELETE(uriBlogPostId, blogIdList.get(0), postIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(PostDocs.Request.removePost())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("포스팅 권한 조회")
  @Docs(existsPathParameters = true)
  public void hasPermissionForPost() throws Exception {
    setSignedManagerMajor();

    apply();

    GET(uriBlogPostIdHasPermission, postIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(PostDocs.Request.hasPermissionForPost())))
        .andExpect(status().isOk());
  }

}
