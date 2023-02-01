package run.freshr.service;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.CONFLICT;
import static run.freshr.common.utils.RestUtil.buildId;
import static run.freshr.common.utils.RestUtil.checkManager;
import static run.freshr.common.utils.RestUtil.error;
import static run.freshr.common.utils.RestUtil.getExceptions;
import static run.freshr.common.utils.RestUtil.getSigned;
import static run.freshr.common.utils.RestUtil.getSignedId;
import static run.freshr.common.utils.RestUtil.ok;
import static run.freshr.domain.blog.enumeration.BlogRole.OWNER;
import static run.freshr.domain.blog.enumeration.BlogRole.VIEWER;
import static run.freshr.domain.blog.enumeration.BlogViewType.PUBLIC;
import static run.freshr.utils.MapperUtil.map;

import java.util.ArrayList;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.common.dto.response.IsResponse;
import run.freshr.common.utils.RestUtil;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.unit.AccountUnit;
import run.freshr.domain.blog.dto.request.BlogCreateRequest;
import run.freshr.domain.blog.dto.request.BlogUpdateRequest;
import run.freshr.domain.blog.dto.request.PostCreateRequest;
import run.freshr.domain.blog.dto.request.PostUpdateRequest;
import run.freshr.domain.blog.dto.response.BlogListResponse;
import run.freshr.domain.blog.dto.response.BlogResponse;
import run.freshr.domain.blog.dto.response.PostListResponse;
import run.freshr.domain.blog.dto.response.PostResponse;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.unit.BlogUnit;
import run.freshr.domain.blog.unit.PostUnit;
import run.freshr.domain.blog.vo.BlogSearch;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.common.unit.AttachUnit;
import run.freshr.domain.common.unit.HashtagUnit;
import run.freshr.domain.mapping.dto.request.BlogPermissionMappingApprovalRequest;
import run.freshr.domain.mapping.dto.request.BlogPermissionMappingSaveRequest;
import run.freshr.domain.mapping.entity.BlogHashtagMapping;
import run.freshr.domain.mapping.entity.BlogPermissionMapping;
import run.freshr.domain.mapping.entity.BlogRequestMapping;
import run.freshr.domain.mapping.entity.PostAttachMapping;
import run.freshr.domain.mapping.entity.PostHashtagMapping;
import run.freshr.domain.mapping.unit.BlogHashtagMappingUnit;
import run.freshr.domain.mapping.unit.BlogPermissionMappingUnit;
import run.freshr.domain.mapping.unit.BlogRequestMappingUnit;
import run.freshr.domain.mapping.unit.PostAttachMappingUnit;
import run.freshr.domain.mapping.unit.PostHashtagMappingUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogServiceImpl implements BlogService {

  private final BlogUnit blogUnit;
  private final PostUnit postUnit;
  private final AttachUnit attachUnit;
  private final HashtagUnit hashtagUnit;
  private final AccountUnit accountUnit;
  private final BlogHashtagMappingUnit blogHashtagMappingUnit;
  private final BlogRequestMappingUnit blogRequestMappingUnit;
  private final BlogPermissionMappingUnit blogPermissionMappingUnit;
  private final PostHashtagMappingUnit postHashtagMappingUnit;
  private final PostAttachMappingUnit postAttachMappingUnit;

  @Override
  @Transactional
  public ResponseEntity<?> createBlog(BlogCreateRequest dto) {
    log.info("BlogService.createBlog");

    Attach cover = null;
    IdRequest<Long> coverRequest = dto.getCover();

    if (!isNull(coverRequest) && !isNull(coverRequest.getId()) && coverRequest.getId() > 0) {
      cover = attachUnit.get(coverRequest.getId());
    }

    Blog entity = Blog.createEntity(dto.getTitle(), dto.getDescription(), cover, dto.getViewType());
    Long id = blogUnit.create(entity);

    ofNullable(dto.getHashtagList()).orElse(new ArrayList<>()).forEach(item -> {
      String hashtagWord = item.getHashtag().getId();
      Boolean exists = hashtagUnit.exists(hashtagWord);
      Hashtag hashtag;

      if (exists) {
        hashtag = hashtagUnit.get(hashtagWord);
      } else {
        hashtag = Hashtag.createEntity(hashtagWord);

        hashtagUnit.create(hashtag);
      }

      blogHashtagMappingUnit
          .create(BlogHashtagMapping.createEntity(entity, hashtag, item.getSort()));
    });

    Account signed = getSigned();

    blogPermissionMappingUnit.create(BlogPermissionMapping.createEntity(entity, signed, OWNER));

    return ok(buildId(id));
  }

  @Override
  public ResponseEntity<?> getBlogPage(BlogSearch search) {
    log.info("BlogService.getBlogPage");

    Page<Blog> entityPage = blogUnit.getPage(search);
    Page<BlogListResponse> page = entityPage.map(item -> map(item, BlogListResponse.class));

    return ok(page);
  }

  @Override
  public ResponseEntity<?> getBlog(Long id) {
    log.info("CommunityService.getBoardNotice");

    if (!blogUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog entity = blogUnit.get(id);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    BlogResponse data = map(entity, BlogResponse.class);

    data.setHasPermission(true);

    Long signedId = getSignedId();
    boolean publicFlag = entity.getViewType().equals(PUBLIC);

    if (checkManager()) {
      return ok(data);
    }

    if (publicFlag) {
      return ok(data);
    }

    Boolean existsPermission = blogPermissionMappingUnit.exists(id, signedId);

    if (existsPermission) {
      return ok(data);
    }

    data.setHasPermission(false);

    return ok(data);
  }

  @Override
  @Transactional
  public ResponseEntity<?> updateBlog(Long id, BlogUpdateRequest dto) {
    log.info("CommunityService.updateBlog");

    if (!blogUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog entity = blogUnit.get(id);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    Attach cover = null;
    IdRequest<Long> coverRequest = dto.getCover();

    if (!isNull(coverRequest) && !isNull(coverRequest.getId()) && coverRequest.getId() > 0) {
      cover = attachUnit.get(coverRequest.getId());
    }

    entity.updateEntity(dto.getTitle(), dto.getDescription(), cover, dto.getViewType());

    blogHashtagMappingUnit.delete(entity);

    ofNullable(dto.getHashtagList()).orElse(new ArrayList<>()).forEach(item -> {
      String hashtagWord = item.getHashtag().getId();
      Boolean exists = hashtagUnit.exists(hashtagWord);
      Hashtag hashtag;

      if (exists) {
        hashtag = hashtagUnit.get(hashtagWord);
      } else {
        hashtag = Hashtag.createEntity(hashtagWord);

        hashtagUnit.create(hashtag);
      }

      blogHashtagMappingUnit
          .create(BlogHashtagMapping.createEntity(entity, hashtag, item.getSort()));
    });

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> removeBlog(Long id) {
    log.info("CommunityService.removeBlog");

    if (!blogUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog entity = blogUnit.get(id);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    entity.removeEntity();

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> requestBlog(Long id) {
    log.info("CommunityService.requestBlog");

    if (!blogUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog entity = blogUnit.get(id);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    Long signedId = getSignedId();
    Boolean existsPermission = blogPermissionMappingUnit.exists(id, signedId);

    if (existsPermission) {
      return error(CONFLICT, "already-permission",
          "AR01", "Already Permission");
    }

    Boolean existsRequest = blogRequestMappingUnit.exists(id, signedId);

    if (existsRequest) {
      return error(CONFLICT, "already-request",
          "AR02", "Already Request");
    }

    Account signed = getSigned();

    blogRequestMappingUnit.create(BlogRequestMapping.createEntity(entity, signed));

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> approvalBlog(Long id, BlogPermissionMappingApprovalRequest dto) {
    log.info("CommunityService.requestBlog");

    if (!blogUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog entity = blogUnit.get(id);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    Long accountId = dto.getAccount().getId();
    Boolean existsPermission = blogPermissionMappingUnit.exists(id, accountId);

    if (existsPermission) {
      return error(CONFLICT, "already-permission",
          "AR01", "Already Permission");
    }

    Boolean existsRequest = blogRequestMappingUnit.exists(id, accountId);

    if (existsRequest) {
      blogRequestMappingUnit.delete(id, accountId);
    }

    Account account = accountUnit.get(accountId);

    blogPermissionMappingUnit.create(BlogPermissionMapping.createEntity(entity, account, VIEWER));

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> permitBlog(Long id, BlogPermissionMappingSaveRequest dto) {
    log.info("CommunityService.permitBlog");

    if (!blogUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog entity = blogUnit.get(id);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    Long accountId = dto.getAccount().getId();
    Boolean existsPermission = blogPermissionMappingUnit.exists(id, accountId);

    if (!existsPermission) {
      return error(getExceptions().getEntityNotFound());
    }

    BlogPermissionMapping blogPermission = blogPermissionMappingUnit.get(id, accountId);

    blogPermission.updateEntity(dto.getRole());

    return ok();
  }

  @Override
  public ResponseEntity<?> hasPermissionForBlog(Long id) {
    log.info("CommunityService.hasPermissionForBlog");

    if (!blogUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog entity = blogUnit.get(id);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    Long signedId = getSignedId();

    Boolean existsPermission = blogPermissionMappingUnit.exists(id, signedId);

    return ok(IsResponse.builder().is(existsPermission).build());
  }

  @Override
  @Transactional
  public ResponseEntity<?> createPost(Long blogId, PostCreateRequest dto) {
    log.info("CommunityService.createPost");

    if (!blogUnit.exists(blogId)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog blog = blogUnit.get(blogId);

    if (blog.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!blog.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    Post entity = Post.createEntity(dto.getTitle(), dto.getContents(), dto.getUseFlag(), blog);
    Long id = postUnit.create(entity);

    ofNullable(dto.getHashtagList()).orElse(new ArrayList<>()).forEach(item -> {
      String hashtagWord = item.getHashtag().getId();
      Boolean exists = hashtagUnit.exists(hashtagWord);
      Hashtag hashtag;

      if (exists) {
        hashtag = hashtagUnit.get(hashtagWord);
      } else {
        hashtag = Hashtag.createEntity(hashtagWord);

        hashtagUnit.create(hashtag);
      }

      postHashtagMappingUnit
          .create(PostHashtagMapping.createEntity(entity, hashtag, item.getSort()));
    });

    ofNullable(dto.getAttachList()).orElse(new ArrayList<>()).forEach(item -> {
      Long attachId = item.getAttach().getId();
      Attach attach = attachUnit.get(attachId);

      postAttachMappingUnit.create(PostAttachMapping.createEntity(entity, attach, item.getSort()));
    });

    return ok(buildId(id));
  }

  @Override
  public ResponseEntity<?> getPostPage(Long blogId, BlogSearch search) {
    log.info("CommunityService.getPostPage");

    if (!blogUnit.exists(blogId)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog blog = blogUnit.get(blogId);

    if (blog.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!blog.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    search.setBlogId(blogId);

    Page<Post> entityPage = postUnit.getPage(search);
    Page<PostListResponse> page = entityPage.map(item -> map(item, PostListResponse.class));

    return ok(page);
  }

  @Override
  public ResponseEntity<?> getPost(Long blogId, Long id) {
    log.info("CommunityService.getPost");

    if (!postUnit.exists(blogId, id)) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!blogUnit.exists(blogId)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog blog = blogUnit.get(blogId);

    if (blog.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!blog.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    Post entity = postUnit.get(id);
    PostResponse data = map(entity, PostResponse.class);

    return ok(data);
  }

  @Override
  @Transactional
  public ResponseEntity<?> updatePost(Long blogId, Long id, PostUpdateRequest dto) {
    log.info("CommunityService.updatePost");

    if (!postUnit.exists(blogId, id)) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!blogUnit.exists(blogId)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog blog = blogUnit.get(blogId);

    if (blog.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!blog.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    Post entity = postUnit.get(id);

    entity.updateEntity(dto.getTitle(), dto.getContents(), dto.getUseFlag());

    postHashtagMappingUnit.delete(entity);

    ofNullable(dto.getHashtagList()).orElse(new ArrayList<>()).forEach(item -> {
      String hashtagWord = item.getHashtag().getId();
      Boolean exists = hashtagUnit.exists(hashtagWord);
      Hashtag hashtag;

      if (exists) {
        hashtag = hashtagUnit.get(hashtagWord);
      } else {
        hashtag = Hashtag.createEntity(hashtagWord);

        hashtagUnit.create(hashtag);
      }

      postHashtagMappingUnit
          .create(PostHashtagMapping.createEntity(entity, hashtag, item.getSort()));
    });

    postAttachMappingUnit.delete(entity);

    ofNullable(dto.getAttachList()).orElse(new ArrayList<>()).forEach(item -> {
      Long attachId = item.getAttach().getId();
      Attach attach = attachUnit.get(attachId);

      postAttachMappingUnit.create(PostAttachMapping.createEntity(entity, attach, item.getSort()));
    });

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> removePost(Long blogId, Long id) {
    log.info("CommunityService.removePost");

    if (!postUnit.exists(blogId, id)) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!blogUnit.exists(blogId)) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog blog = blogUnit.get(blogId);

    if (blog.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!blog.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    Post entity = postUnit.get(id);

    entity.removeEntity();

    return ok();
  }

  @Override
  public ResponseEntity<?> hasPermissionForPost(Long id) {
    log.info("CommunityService.hasPermissionForPost");

    if (!postUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    Post entity = postUnit.get(id);

    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    Blog blog = entity.getBlog();

    if (blog.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    Long signedId = getSignedId();

    if (!blog.getUseFlag() && !Objects.equals(signedId, blog.getCreator().getId())) {
      return error(getExceptions().getEntityNotFound());
    }

    Boolean existsPermission = blogPermissionMappingUnit.exists(id, signedId);

    return ok(IsResponse.builder().is(existsPermission).build());
  }

}
