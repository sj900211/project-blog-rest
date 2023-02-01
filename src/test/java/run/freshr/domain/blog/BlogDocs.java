package run.freshr.domain.blog;

import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static run.freshr.domain.blog.entity.QBlog.blog;
import static run.freshr.domain.mapping.entity.QBlogPermissionMapping.blogPermissionMapping;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;
import run.freshr.domain.auth.SignDocs;
import run.freshr.domain.blog.vo.FBlogSearch;
import run.freshr.domain.common.AttachDocs;
import run.freshr.domain.common.HashtagDocs;

@Slf4j
public class BlogDocs {

  public static class Request {
    public static List<FieldDescriptor> createBlog() {
      log.info("BlogDocs.createBlog");

      return PrintUtil
          .builder()

          .prefixDescription("블로그")

          .linkField("blog-docs-create-blog-view-type", blog.viewType)
          .field(blog.title)

          .prefixOptional()

          .field(blog.description)

          .prefixDescription("커버 이미지")
          .field(blog.cover.id)

          .prefixDescription("해시태그")
          .field(blog.hashtagList.any().sort, blog.hashtagList.any().hashtag.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> getBlogPage() {
      log.info("BlogDocs.getBlogPage");

      return PrintUtil
          .builder()

          .parameter(FBlogSearch.page, FBlogSearch.size)

          .prefixOptional()
          .linkParameter("blog-docs-get-blog-page-key", FBlogSearch.key)
          .parameter(FBlogSearch.cursor, FBlogSearch.word)

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getBlog() {
      log.info("BlogDocs.getBlog");

      return PrintUtil
          .builder()

          .prefixDescription("블로그")

          .parameter(blog.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> updateBlogPath() {
      log.info("BlogDocs.updateBlogPath");

      return PrintUtil
          .builder()

          .prefixDescription("블로그")

          .parameter(blog.id)

          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> updateBlog() {
      log.info("BlogDocs.updateBlog");

      return PrintUtil
          .builder()

          .prefixDescription("블로그")

          .linkField("blog-docs-update-blog-view-type", blog.viewType)
          .field(blog.title)

          .prefixOptional()

          .field(blog.description)

          .prefixDescription("커버 이미지")
          .field(blog.cover.id)

          .prefixDescription("해시태그")
          .field(blog.hashtagList.any().sort, blog.hashtagList.any().hashtag.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> removeBlog() {
      log.info("BlogDocs.removeBlog");

      return PrintUtil
          .builder()

          .prefixDescription("블로그")

          .parameter(blog.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> requestBlog() {
      log.info("BlogDocs.requestBlog");

      return PrintUtil
          .builder()

          .prefixDescription("블로그")

          .parameter(blog.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> approvalBlogPath() {
      log.info("BlogDocs.approvalBlogPath");

      return PrintUtil
          .builder()

          .prefixDescription("블로그")

          .parameter(blog.id)

          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> approvalBlog() {
      log.info("BlogDocs.approvalBlog");

      return PrintUtil
          .builder()

          .prefixDescription("승인 계정")

          .field(blogPermissionMapping.account.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> permitBlogPath() {
      log.info("BlogDocs.permitBlogPath");

      return PrintUtil
          .builder()

          .prefixDescription("블로그")

          .parameter(blog.id)

          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> permitBlog() {
      log.info("BlogDocs.permitBlog");

      return PrintUtil
          .builder()

          .prefixDescription("블로그")

          .linkField("blog-docs-permit-blog-role", blogPermissionMapping.role)

          .prefixDescription("승인 계정")

          .field(blogPermissionMapping.account.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> hasPermissionForBlog() {
      log.info("BlogDocs.hasPermissionForBlog");

      return PrintUtil
          .builder()

          .prefixDescription("블로그")

          .parameter(blog.id)

          .build()
          .getParameterList();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> createBlog() {
      log.info("BlogDocs.createBlog");

      return ResponseDocs
          .data()

          .prefixDescription("블로그")

          .field(blog.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getBlogPage() {
      log.info("BlogDocs.getBlogPage");

      return ResponseDocs
          .page()

          .prefixDescription("블로그")

          .linkField("blog-docs-get-blog-page-view-type", blog.viewType)
          .field(blog.id, blog.title, blog.createAt, blog.updateAt)

          .addField(SignDocs.Docs.setAuditor("page.content[].creator",
              "작성자", false))
          .addField(SignDocs.Docs.setAuditor("page.content[].updater",
              "마지막 수정자", false))

          .prefixOptional()

          .field(blog.description)

          .addField(AttachDocs.Docs
              .setAttach("page.content[].cover", "커버 이미지", true))

          .prefixDescription("해시태그")

          .field(blog.hashtagList.any().sort)
          .addField(HashtagDocs.Docs.setHashtag("page.content[].hashtagList[].hashtag",
              "해시태그", true))

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getBlog() {
      log.info("BlogDocs.getBlog");

      return ResponseDocs
          .data()

          .prefixDescription("블로그")

          .field("hasPermission", "권한 여부", BOOLEAN)
          .linkField("blog-docs-get-blog-view-type", blog.viewType)
          .field(blog.id, blog.title, blog.createAt, blog.updateAt)

          .addField(SignDocs.Docs.setAuditor("data.creator",
              "작성자", false))
          .addField(SignDocs.Docs.setAuditor("data.updater",
              "마지막 수정자", false))

          .prefixOptional()

          .field(blog.description)

          .addField(AttachDocs.Docs
              .setAttach("data.cover", "커버 이미지", true))

          .prefixDescription("해시태그")

          .field(blog.hashtagList.any().sort)
          .addField(HashtagDocs.Docs.setHashtag("data.hashtagList[].hashtag",
              "해시태그", true))

          .clear()
          .build()
          .getFieldList();
    }
  }

  public static class Docs {

  }

}
