package run.freshr.service;

import java.util.List;
import java.util.Map;
import run.freshr.TestRunner;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.enumeration.Privilege;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.enumeration.BlogRole;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.community.entity.BoardNotice;
import run.freshr.domain.community.enumeration.BoardNoticeExpose;
import run.freshr.mappers.EnumGetter;

/**
 * Test service.
 *
 * @author FreshR
 * @apiNote {@link TestRunner} 에서 데이터 설정을 쉽게하기 위해서 데이터 생성 기능을 재정의<br> 필수 작성은 아니며, 테스트 코드에서 데이터 생성
 * 기능을 조금이라도 더 편하게 사용하고자 만든 Service<br> 권한과 같은 특수한 경우를 제외한 대부분은 데이터에 대한 Create, Get 정도만 작성해서 사용을
 * 한다.
 * @since 2023. 1. 13. 오전 11:35:07
 */
public interface TestService {

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|

  Map<String, List<EnumGetter>> getEnumAll();

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  long createAttach(String filename, String path);

  Attach getAttach(long id);

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  void createHashtag(String id);

  Hashtag getHashtag(String id);

  //      ___      __    __  .___________. __    __
  //     /   \    |  |  |  | |           ||  |  |  |
  //    /  ^  \   |  |  |  | `---|  |----`|  |__|  |
  //   /  /_\  \  |  |  |  |     |  |     |   __   |
  //  /  _____  \ |  `--'  |     |  |     |  |  |  |
  // /__/     \__\ \______/      |__|     |__|  |__|

  void createRsa();

  void createAuth(Long id, Role role);

  void createAccess(String accessToken, Long id, Role role);

  AccessRedis getAccess(String id);

  void createRefresh(String refreshToken, String access);

  long createAccount(String username, String name);

  long createAccount(Privilege privilege, String username, String name);

  Account getAccount(long id);

  // .__   __.   ______   .___________. __    ______  _______
  // |  \ |  |  /  __  \  |           ||  |  /      ||   ____|
  // |   \|  | |  |  |  | `---|  |----`|  | |  ,----'|  |__
  // |  . `  | |  |  |  |     |  |     |  | |  |     |   __|
  // |  |\   | |  `--'  |     |  |     |  | |  `----.|  |____
  // |__| \__|  \______/      |__|     |__|  \______||_______|

  long createBoardNotice(String title, String contents, Boolean fixed, BoardNoticeExpose expose);

  BoardNotice getBoardNotice(long id);

  void createBoardNoticeAttachMapping(long noticeId, long attachId, int sort);

  // .______    __        ______     _______
  // |   _  \  |  |      /  __  \   /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |
  // |______/  |_______| \______/   \______|
  long createBlog(String title, String description, long coverId);

  Blog getBlog(long id);

  void createBlogHashtagMapping(Blog blog, Hashtag hashtag, Integer sort);

  void createBlogRequestMapping(Blog blog, long accountId);

  void createBlogPermissionMapping(Blog blog, long accountId, BlogRole role);

  // .______     ______        _______.___________.
  // |   _  \   /  __  \      /       |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |
  // |  |      |  `--'  | .----)   |      |  |
  // | _|       \______/  |_______/       |__|
  long createPost(String title, String contents, Blog blog);

  Post getPost(long id);

  void createPostAttachMapping(Post post, long attachId, Integer sort);

  void createPostHashtagMapping(Post post, Hashtag hashtag, Integer sort);

}
