package run.freshr.service;

import org.springframework.http.ResponseEntity;
import run.freshr.domain.blog.dto.request.BlogCreateRequest;
import run.freshr.domain.blog.dto.request.BlogUpdateRequest;
import run.freshr.domain.blog.dto.request.PostCreateRequest;
import run.freshr.domain.blog.dto.request.PostUpdateRequest;
import run.freshr.domain.blog.vo.BlogSearch;
import run.freshr.domain.mapping.dto.request.BlogPermissionMappingApprovalRequest;
import run.freshr.domain.mapping.dto.request.BlogPermissionMappingSaveRequest;

public interface BlogService {

  ResponseEntity<?> createBlog(BlogCreateRequest dto);

  ResponseEntity<?> getBlogPage(BlogSearch search);

  ResponseEntity<?> getBlog(Long id);

  ResponseEntity<?> updateBlog(Long id, BlogUpdateRequest dto);

  ResponseEntity<?> removeBlog(Long id);

  ResponseEntity<?> requestBlog(Long id);

  ResponseEntity<?> approvalBlog(Long id, BlogPermissionMappingApprovalRequest dto);

  ResponseEntity<?> permitBlog(Long id, BlogPermissionMappingSaveRequest dto);

  ResponseEntity<?> hasPermissionForBlog(Long id);

  ResponseEntity<?> createPost(Long blogId, PostCreateRequest dto);

  ResponseEntity<?> getPostPage(Long blogId, BlogSearch search);

  ResponseEntity<?> getPost(Long blogId, Long id);

  ResponseEntity<?> updatePost(Long blogId, Long id, PostUpdateRequest dto);

  ResponseEntity<?> removePost(Long blogId, Long id);

  ResponseEntity<?> hasPermissionForPost(Long id);

}
