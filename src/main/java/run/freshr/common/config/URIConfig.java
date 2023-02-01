package run.freshr.common.config;

/**
 * URI 설정.
 *
 * @author FreshR
 * @apiNote URI 를 한 곳에서 관리하기 위한 클래스
 * @since 2023. 1. 12. 오후 5:59:42
 */
public class URIConfig {

  public static final String uriH2All = "/h2/**"; // 로컬에서 H2 DB 를 확인하는 URI
  public static final String uriDocsAll = "/docs/**"; // API 문서 URI
  public static final String uriFavicon = "/favicon.ico"; // 파비콘 URL

  public static final String uriDocsIndex = "/docs";
  public static final String uriDocsDepth1 = "/docs/{depth1}";
  public static final String uriDocsDepth2 = "/docs/{depth1}/{depth2}";
  public static final String uriDocsDepth3 = "/docs/{depth1}/{depth2}/{depth3}";

  public static final String uriCommonHeartbeat = "/heartbeat"; // 서비스가 실행되었는지 체크하기 위한 URI
  public static final String uriCommonEnum = "/enum";
  public static final String uriCommonEnumPick = "/enum/{pick}";
  public static final String uriCommonAttach = "/file";
  public static final String uriCommonAttachId = "/file/{id}";
  public static final String uriCommonAttachIdDownload = "/file/{id}/download";
  public static final String uriCommonAttachExist = "/file/{id}/exist";
  public static final String uriCommonHashtagKeyword = "/hashtag/{keyword}";
  public static final String uriCommonHashtagStatistics = "/hashtag/statistics";
  public static final String uriCommonHashtagKeywordBlog = "/hashtag/{keyword}/blog";
  public static final String uriCommonHashtagKeywordPost = "/hashtag/{keyword}/post";

  public static final String uriAuthCrypto = "/auth/crypto";
  public static final String uriAuthToken = "/auth/token";
  public static final String uriAuthSignIn = "/auth/sign-in";
  public static final String uriAuthSignOut = "/auth/sign-out";
  public static final String uriAuthPassword = "/auth/password";
  public static final String uriAuthInfo = "/auth/info";

  public static final String uriCommunityNotice = "/community/notice";
  public static final String uriCommunityNoticeId = "/community/notice/{id}";

  public static final String uriBlog = "/blog";
  public static final String uriBlogId = "/blog/{id}";
  public static final String uriBlogIdRequest = "/blog/{id}/request";
  public static final String uriBlogIdApproval = "/blog/{id}/approval";
  public static final String uriBlogIdPermit = "/blog/{id}/permit";
  public static final String uriBlogIdHasPermission = "/blog/{id}/has-permission";
  public static final String uriBlogPost = "/blog/{blogId}/post";
  public static final String uriBlogPostId = "/blog/{blogId}/post/{id}";
  public static final String uriBlogPostIdHasPermission = "/blog/post/{id}/has-permission";

}
