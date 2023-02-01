package run.freshr.domain.blog.permission;

import static run.freshr.common.utils.RestUtil.getSignedId;
import static run.freshr.common.utils.RestUtil.getSignedRole;
import static run.freshr.domain.auth.enumeration.Role.ROLE_MANAGER_MAJOR;
import static run.freshr.domain.auth.enumeration.Role.ROLE_MANAGER_MINOR;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.enumeration.BlogRole;
import run.freshr.domain.blog.unit.PostUnit;
import run.freshr.domain.mapping.dto.request.BlogPermissionMappingSaveRequest;
import run.freshr.domain.mapping.entity.BlogPermissionMapping;
import run.freshr.domain.mapping.unit.BlogPermissionMappingUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlogPermission {

  private final PostUnit postUnit;
  private final BlogPermissionMappingUnit blogPermissionMappingUnit;

  public boolean updateBlog(Long id) {
    log.info("BlogPermission.updateBlog");

    Role signedRole = getSignedRole();

    if (signedRole.check(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR)) {
      return true;
    }

    Long signedId = getSignedId();

    Boolean existsPermission = blogPermissionMappingUnit.exists(id, signedId);

    if (!existsPermission) {
      return false;
    }

    BlogPermissionMapping blogPermission = blogPermissionMappingUnit.get(id, signedId);
    BlogRole role = blogPermission.getRole();
    Boolean blog = role.getBlog();

    return blog;
  }

  public boolean removeBlog(Long id) {
    log.info("BlogPermission.removeBlog");

    Role signedRole = getSignedRole();

    if (signedRole.check(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR)) {
      return true;
    }

    Long signedId = getSignedId();

    Boolean existsPermission = blogPermissionMappingUnit.exists(id, signedId);

    if (!existsPermission) {
      return false;
    }

    BlogPermissionMapping blogPermission = blogPermissionMappingUnit.get(id, signedId);
    BlogRole role = blogPermission.getRole();
    Boolean hasPermission = role.getBlog();

    return hasPermission;
  }

  public boolean approvalBlog(Long id) {
    log.info("BlogPermission.approvalBlog");

    Role signedRole = getSignedRole();

    if (signedRole.check(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR)) {
      return true;
    }

    Long signedId = getSignedId();

    Boolean existsPermission = blogPermissionMappingUnit.exists(id, signedId);

    if (!existsPermission) {
      return false;
    }

    BlogPermissionMapping blogPermission = blogPermissionMappingUnit.get(id, signedId);
    BlogRole role = blogPermission.getRole();
    Boolean hasPermission = role.getApprove();

    return hasPermission;
  }

  public boolean permitBlog(Long id, BlogPermissionMappingSaveRequest dto) {
    log.info("BlogPermission.permitBlog");

    Role signedRole = getSignedRole();

    if (signedRole.check(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR)) {
      return true;
    }

    Long signedId = getSignedId();

    Boolean existsPermission = blogPermissionMappingUnit.exists(id, signedId);

    if (!existsPermission) {
      return false;
    }

    BlogPermissionMapping blogPermission = blogPermissionMappingUnit.get(id, signedId);
    BlogRole role = blogPermission.getRole();
    Boolean hasPermission = role.getBlog();

    if (!hasPermission) {
      return false;
    }

    Long accountId = dto.getAccount().getId();

    Boolean targetExistsPermission = blogPermissionMappingUnit.exists(id, accountId);

    if (!targetExistsPermission) {
      return false;
    }

    BlogPermissionMapping targetBlogPermission = blogPermissionMappingUnit.get(id, accountId);
    BlogRole targetRole = targetBlogPermission.getRole();

    return targetRole.getOrder() <= role.getOrder();
  }

  public boolean createPost(Long blogId) {
    log.info("BlogPermission.permitBlog");

    Role signedRole = getSignedRole();

    if (signedRole.check(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR)) {
      return true;
    }

    Long signedId = getSignedId();

    Boolean existsPermission = blogPermissionMappingUnit.exists(blogId, signedId);

    if (!existsPermission) {
      return false;
    }

    BlogPermissionMapping blogPermission = blogPermissionMappingUnit.get(blogId, signedId);
    BlogRole role = blogPermission.getRole();
    Boolean hasPermission = role.getPosting();

    return hasPermission;
  }

  public boolean getPostPage(Long blogId) {
    log.info("BlogPermission.getPostPage");

    Role signedRole = getSignedRole();

    if (signedRole.check(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR)) {
      return true;
    }

    Long signedId = getSignedId();

    Boolean existsPermission = blogPermissionMappingUnit.exists(blogId, signedId);

    return existsPermission;
  }

  public boolean getPost(Long blogId) {
    log.info("BlogPermission.getPost");

    Role signedRole = getSignedRole();

    if (signedRole.check(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR)) {
      return true;
    }

    Long signedId = getSignedId();

    Boolean existsPermission = blogPermissionMappingUnit.exists(blogId, signedId);

    return existsPermission;
  }

  public boolean updatePost(Long blogId, Long id) {
    log.info("BlogPermission.updatePost");

    Role signedRole = getSignedRole();

    if (signedRole.check(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR)) {
      return true;
    }

    Long signedId = getSignedId();

    Boolean existsPermission = blogPermissionMappingUnit.exists(blogId, signedId);

    if (!existsPermission) {
      return false;
    }

    BlogPermissionMapping blogPermission = blogPermissionMappingUnit.get(blogId, signedId);
    BlogRole role = blogPermission.getRole();
    Boolean permissionPosting = role.getPosting();

    if (!permissionPosting) {
      return false;
    }

    Boolean permissionOwner = role.getBlog();

    if (permissionOwner) {
      return true;
    }

    Post post = postUnit.get(id);
    Account creator = post.getCreator();
    Long createBy = creator.getId();

    return Objects.equals(createBy, signedId);
  }

  public boolean removePost(Long blogId, Long id) {
    log.info("BlogPermission.removePost");

    Role signedRole = getSignedRole();

    if (signedRole.check(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR)) {
      return true;
    }

    Long signedId = getSignedId();

    Boolean existsPermission = blogPermissionMappingUnit.exists(blogId, signedId);

    if (!existsPermission) {
      return false;
    }

    BlogPermissionMapping blogPermission = blogPermissionMappingUnit.get(blogId, signedId);
    BlogRole role = blogPermission.getRole();
    Boolean permissionPosting = role.getPosting();

    if (!permissionPosting) {
      return false;
    }

    Boolean permissionOwner = role.getBlog();

    if (permissionOwner) {
      return true;
    }

    Post post = postUnit.get(id);
    Account creator = post.getCreator();
    Long createBy = creator.getId();

    return Objects.equals(createBy, signedId);
  }

}
