package run.freshr.service;

import static run.freshr.common.util.ThreadUtil.threadAccess;
import static run.freshr.common.util.ThreadUtil.threadPublicKey;
import static run.freshr.common.util.ThreadUtil.threadRefresh;
import static run.freshr.domain.blog.enumeration.BlogViewType.PUBLIC;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import run.freshr.TestRunner;
import run.freshr.common.security.TokenProvider;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.enumeration.Privilege;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;
import run.freshr.domain.auth.redis.RsaPair;
import run.freshr.domain.auth.unit.AccessRedisUnit;
import run.freshr.domain.auth.unit.AccountUnit;
import run.freshr.domain.auth.unit.RefreshRedisUnit;
import run.freshr.domain.auth.unit.RsaPairUnit;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.enumeration.BlogRole;
import run.freshr.domain.blog.enumeration.BlogViewType;
import run.freshr.domain.blog.unit.BlogUnit;
import run.freshr.domain.blog.unit.PostUnit;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.common.unit.AttachUnit;
import run.freshr.domain.common.unit.HashtagUnit;
import run.freshr.domain.community.entity.BoardNotice;
import run.freshr.domain.community.enumeration.BoardNoticeExpose;
import run.freshr.domain.community.unit.BoardNoticeUnit;
import run.freshr.domain.mapping.entity.BlogHashtagMapping;
import run.freshr.domain.mapping.entity.BlogPermissionMapping;
import run.freshr.domain.mapping.entity.BlogRequestMapping;
import run.freshr.domain.mapping.entity.BoardNoticeAttachMapping;
import run.freshr.domain.mapping.entity.PostAttachMapping;
import run.freshr.domain.mapping.entity.PostHashtagMapping;
import run.freshr.domain.mapping.unit.BlogHashtagMappingUnit;
import run.freshr.domain.mapping.unit.BlogPermissionMappingUnit;
import run.freshr.domain.mapping.unit.BlogRequestMappingUnit;
import run.freshr.domain.mapping.unit.BoardNoticeAttachMappingUnit;
import run.freshr.domain.mapping.unit.PostAttachMappingUnit;
import run.freshr.domain.mapping.unit.PostHashtagMappingUnit;
import run.freshr.mappers.EnumGetter;
import run.freshr.mappers.EnumMapper;
import run.freshr.utils.CryptoUtil;

/**
 * Test service.
 *
 * @author FreshR
 * @apiNote {@link TestRunner} 에서 데이터 설정을 쉽게하기 위해서 데이터 생성 기능을 재정의<br> 필수 작성은 아니며, 테스트 코드에서 데이터 생성
 * 기능을 조금이라도 더 편하게 사용하고자 만든 Service<br> 권한과 같은 특수한 경우를 제외한 대부분은 데이터에 대한 Create, Get 정도만 작성해서 사용을
 * 한다.
 * @since 2023. 1. 13. 오전 11:35:07
 */
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

  private final PasswordEncoder passwordEncoder;
  private final TokenProvider provider;

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|
  private final EnumMapper enumMapper;

  @Override
  public Map<String, List<EnumGetter>> getEnumAll() {
    return enumMapper.getAll();
  }

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  private final AttachUnit attachUnit;

  @Override
  public long createAttach(String filename, String path) {
    return attachUnit.create(Attach.createEntity(
        "image/png",
        filename,
        path + "/" + filename,
        2048L,
        "alt",
        "title"
    ));
  }

  @Override
  public Attach getAttach(long id) {
    return attachUnit.get(id);
  }

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  private final HashtagUnit hashtagUnit;

  @Override
  public void createHashtag(String id) {
    hashtagUnit.create(Hashtag.createEntity(id));
  }

  @Override
  public Hashtag getHashtag(String id) {
    return hashtagUnit.get(id);
  }

  //      ___      __    __  .___________. __    __
  //     /   \    |  |  |  | |           ||  |  |  |
  //    /  ^  \   |  |  |  | `---|  |----`|  |__|  |
  //   /  /_\  \  |  |  |  |     |  |     |   __   |
  //  /  _____  \ |  `--'  |     |  |     |  |  |  |
  // /__/     \__\ \______/      |__|     |__|  |__|
  private final RsaPairUnit rsaPairUnit;
  private final AccessRedisUnit authAccessUnit;
  private final RefreshRedisUnit authRefreshUnit;
  private final AccountUnit accountUnit;

  @Override
  public void createRsa() {
    KeyPair keyPar = CryptoUtil.getKeyPar();
    PublicKey publicKey = keyPar.getPublic();
    PrivateKey privateKey = keyPar.getPrivate();
    String encodePublicKey = CryptoUtil.encodePublicKey(publicKey);
    String encodePrivateKey = CryptoUtil.encodePrivateKey(privateKey);

    threadPublicKey.set(encodePublicKey);

    rsaPairUnit.save(RsaPair.createRedis(encodePublicKey, encodePrivateKey, LocalDateTime.now()));
  }

  @Override
  public void createAuth(Long id, Role role) {
    threadAccess.remove();
    threadRefresh.remove();

    // 토큰 발급
    String accessToken = provider.generateAccessToken(id);
    String refreshToken = provider.generateRefreshToken(id);

    threadAccess.set(accessToken);
    threadRefresh.set(refreshToken);

    // 토큰 등록
    authAccessUnit.save(AccessRedis.createRedis(accessToken, id, role));
    authRefreshUnit.save(RefreshRedis.createRedis(refreshToken, authAccessUnit.get(accessToken)));
  }

  @Override
  public void createAccess(String accessToken, Long id, Role role) {
    authAccessUnit.save(AccessRedis.createRedis(accessToken, id, role));
  }

  @Override
  public AccessRedis getAccess(String id) {
    return authAccessUnit.get(id);
  }

  @Override
  public void createRefresh(String refreshToken, String access) {
    authRefreshUnit.save(RefreshRedis.createRedis(refreshToken, getAccess(access)));
  }

  @Override
  public long createAccount(String username, String name) {
    return accountUnit.create(Account.createEntity(username,
        passwordEncoder.encode("1234"), name));
  }

  @Override
  public long createAccount(Privilege privilege, String username, String name) {
    Account entity = Account.createEntity(username,
        passwordEncoder.encode("1234"), name);

    entity.updatePrivilege(privilege);

    return accountUnit.create(entity);
  }

  @Override
  public Account getAccount(long id) {
    return accountUnit.get(id);
  }

  // .__   __.   ______   .___________. __    ______  _______
  // |  \ |  |  /  __  \  |           ||  |  /      ||   ____|
  // |   \|  | |  |  |  | `---|  |----`|  | |  ,----'|  |__
  // |  . `  | |  |  |  |     |  |     |  | |  |     |   __|
  // |  |\   | |  `--'  |     |  |     |  | |  `----.|  |____
  // |__| \__|  \______/      |__|     |__|  \______||_______|

  private final BoardNoticeUnit boardNoticeUnit;
  private final BoardNoticeAttachMappingUnit boardNoticeAttachMappingUnit;

  @Override
  public long createBoardNotice(String title, String contents, Boolean fixed,
      BoardNoticeExpose expose) {
    return boardNoticeUnit
        .create(BoardNotice.createEntity(title, contents, fixed, expose));
  }

  @Override
  public BoardNotice getBoardNotice(long id) {
    return boardNoticeUnit.get(id);
  }

  @Override
  public void createBoardNoticeAttachMapping(long noticeId, long attachId, int sort) {
    boardNoticeAttachMappingUnit.create(BoardNoticeAttachMapping
        .createEntity(getBoardNotice(noticeId), getAttach(attachId), sort));
  }

  // .______    __        ______     _______
  // |   _  \  |  |      /  __  \   /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |
  // |______/  |_______| \______/   \______|
  private final BlogUnit blogUnit;
  private final BlogHashtagMappingUnit blogHashtagMappingUnit;
  private final BlogRequestMappingUnit blogRequestMappingUnit;
  private final BlogPermissionMappingUnit blogPermissionMappingUnit;

  @Override
  public long createBlog(String title, String description, long coverId) {
    return blogUnit.create(Blog.createEntity(title, description, attachUnit.get(coverId), PUBLIC));
  }

  @Override
  public Blog getBlog(long id) {
    return blogUnit.get(id);
  }

  @Override
  public void createBlogHashtagMapping(Blog blog, Hashtag hashtag, Integer sort) {
    blogHashtagMappingUnit.create(BlogHashtagMapping.createEntity(blog, hashtag, sort));
  }
  @Override
  public void createBlogRequestMapping(Blog blog, long accountId) {
    blogRequestMappingUnit.create(BlogRequestMapping.createEntity(blog, getAccount(accountId)));
  }
  @Override
  public void createBlogPermissionMapping(Blog blog, long accountId, BlogRole role) {
    blogPermissionMappingUnit
        .create(BlogPermissionMapping.createEntity(blog, getAccount(accountId), role));
  }

  // .______     ______        _______.___________.
  // |   _  \   /  __  \      /       |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |
  // |  |      |  `--'  | .----)   |      |  |
  // | _|       \______/  |_______/       |__|
  private final PostUnit postUnit;
  private final PostAttachMappingUnit postAttachMappingUnit;
  private final PostHashtagMappingUnit postHashtagMappingUnit;

  @Override
  public long createPost(String title, String contents, Blog blog) {
    return postUnit.create(Post.createEntity(title, contents, true, blog));
  }

  @Override
  public Post getPost(long id) {
    return postUnit.get(id);
  }

  @Override
  public void createPostAttachMapping(Post post, long attachId, Integer sort) {
    postAttachMappingUnit.create(PostAttachMapping.createEntity(post, getAttach(attachId), sort));
  }

  @Override
  public void createPostHashtagMapping(Post post, Hashtag hashtag, Integer sort) {
    postHashtagMappingUnit.create(PostHashtagMapping.createEntity(post, hashtag, sort));
  }

}
