package run.freshr.controller;

import static run.freshr.common.config.URIConfig.uriBlog;
import static run.freshr.common.config.URIConfig.uriBlogId;
import static run.freshr.common.config.URIConfig.uriBlogIdApproval;
import static run.freshr.common.config.URIConfig.uriBlogIdHasPermission;
import static run.freshr.common.config.URIConfig.uriBlogIdPermit;
import static run.freshr.common.config.URIConfig.uriBlogIdRequest;
import static run.freshr.common.config.URIConfig.uriBlogPost;
import static run.freshr.common.config.URIConfig.uriBlogPostId;
import static run.freshr.common.config.URIConfig.uriBlogPostIdHasPermission;
import static run.freshr.common.utils.RestUtil.error;
import static run.freshr.domain.auth.enumeration.Role.Secured.ANONYMOUS;
import static run.freshr.domain.auth.enumeration.Role.Secured.MANAGER_MAJOR;
import static run.freshr.domain.auth.enumeration.Role.Secured.MANAGER_MINOR;
import static run.freshr.domain.auth.enumeration.Role.Secured.USER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.freshr.domain.blog.dto.request.BlogCreateRequest;
import run.freshr.domain.blog.dto.request.BlogUpdateRequest;
import run.freshr.domain.blog.dto.request.PostCreateRequest;
import run.freshr.domain.blog.dto.request.PostUpdateRequest;
import run.freshr.domain.blog.validator.BlogValidator;
import run.freshr.domain.blog.vo.BlogSearch;
import run.freshr.domain.mapping.dto.request.BlogPermissionMappingApprovalRequest;
import run.freshr.domain.mapping.dto.request.BlogPermissionMappingSaveRequest;
import run.freshr.service.BlogService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BlogController {

  private final BlogService service;
  private final BlogValidator validator;

  // .______    __        ______     _______
  // |   _  \  |  |      /  __  \   /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |
  // |______/  |_______| \______/   \______|

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PostMapping(uriBlog)
  public ResponseEntity<?> createBlog(@RequestBody @Valid BlogCreateRequest dto) {
    log.info("BlogController.createBlog");

    return service.createBlog(dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriBlog)
  public ResponseEntity<?> getBlogPage(@ModelAttribute BlogSearch search, Errors errors) {
    log.info("BlogController.getBlogPage");

    validator.getBlogPage(search, errors);

    if (errors.hasErrors()) {
      return error(errors);
    }

    return service.getBlogPage(search);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriBlogId)
  public ResponseEntity<?> getBlog(@PathVariable Long id) {
    log.info("BlogController.getBlog");

    return service.getBlog(id);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PreAuthorize("@blogPermission.updateBlog(#id)")
  @PutMapping(uriBlogId)
  public ResponseEntity<?> updateBlog(@PathVariable Long id,
      @RequestBody @Valid BlogUpdateRequest dto) {
    log.info("BlogController.updateBlog");

    return service.updateBlog(id, dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PreAuthorize("@blogPermission.removeBlog(#id)")
  @DeleteMapping(uriBlogId)
  public ResponseEntity<?> removeBlog(@PathVariable Long id) {
    log.info("BlogController.removeBlog");

    return service.removeBlog(id);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PostMapping(uriBlogIdRequest)
  public ResponseEntity<?> requestBlog(@PathVariable Long id) {
    log.info("BlogController.requestBlog");

    return service.requestBlog(id);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PreAuthorize("@blogPermission.approvalBlog(#id)")
  @PostMapping(uriBlogIdApproval)
  public ResponseEntity<?> approvalBlog(@PathVariable Long id,
      @RequestBody @Valid BlogPermissionMappingApprovalRequest dto) {
    log.info("BlogController.approvalBlog");

    return service.approvalBlog(id, dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PreAuthorize("@blogPermission.permitBlog(#id, #dto)")
  @PutMapping(uriBlogIdPermit)
  public ResponseEntity<?> permitBlog(@PathVariable Long id,
      @RequestBody @Valid BlogPermissionMappingSaveRequest dto) {
    log.info("BlogController.permitBlog");

    return service.permitBlog(id, dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @GetMapping(uriBlogIdHasPermission)
  public ResponseEntity<?> hasPermissionForBlog(@PathVariable Long id) {
    log.info("BlogController.hasPermissionForBlog");

    return service.hasPermissionForBlog(id);
  }

  // .______     ______        _______.___________.
  // |   _  \   /  __  \      /       |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |
  // |  |      |  `--'  | .----)   |      |  |
  // | _|       \______/  |_______/       |__|

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PreAuthorize("@blogPermission.createPost(#blogId)")
  @PostMapping(uriBlogPost)
  public ResponseEntity<?> createPost(@PathVariable Long blogId,
      @RequestBody @Valid PostCreateRequest dto) {
    log.info("BlogController.createPost");

    return service.createPost(blogId, dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @PreAuthorize("@blogPermission.getPostPage(#blogId)")
  @GetMapping(uriBlogPost)
  public ResponseEntity<?> getPostPage(@PathVariable Long blogId,
      @ModelAttribute BlogSearch search, Errors errors) {
    log.info("BlogController.getPostPage");

    validator.getPostPage(search, errors);

    if (errors.hasErrors()) {
      return error(errors);
    }

    return service.getPostPage(blogId, search);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @PreAuthorize("@blogPermission.getPost(#blogId)")
  @GetMapping(uriBlogPostId)
  public ResponseEntity<?> getPost(@PathVariable Long blogId, @PathVariable Long id) {
    log.info("BlogController.getPost");

    return service.getPost(blogId, id);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PreAuthorize("@blogPermission.updatePost(#blogId, #id)")
  @PutMapping(uriBlogPostId)
  public ResponseEntity<?> updatePost(@PathVariable Long blogId, @PathVariable Long id,
      @RequestBody @Valid PostUpdateRequest dto) {
    log.info("BlogController.updatePost");

    return service.updatePost(blogId, id, dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PreAuthorize("@blogPermission.removePost(#blogId, #id)")
  @DeleteMapping(uriBlogPostId)
  public ResponseEntity<?> removePost(@PathVariable Long blogId, @PathVariable Long id) {
    log.info("BlogController.removePost");

    return service.removePost(blogId, id);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @GetMapping(uriBlogPostIdHasPermission)
  public ResponseEntity<?> hasPermissionForPost(@PathVariable Long id) {
    log.info("BlogController.hasPermissionForPost");

    return service.hasPermissionForPost(id);
  }

}
