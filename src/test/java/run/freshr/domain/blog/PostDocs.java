package run.freshr.domain.blog;

import static run.freshr.domain.blog.entity.QPost.post;

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
public class PostDocs {

  public static class Request {

    public static List<ParameterDescriptor> createPostPath() {
      log.info("PostDocs.createPostPath");

      return PrintUtil
          .builder()

          .parameter("blogId", "블로그 일련 번호")

          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> createPost() {
      log.info("PostDocs.createPost");

      return PrintUtil
          .builder()

          .prefixDescription("포스팅")

          .field(post.title, post.contents, post.useFlag)

          .prefixOptional()

          .prefixDescription("해시태그")
          .field(post.hashtagList.any().sort, post.hashtagList.any().hashtag.id)

          .prefixDescription("첨부 파일")
          .field(post.attachList.any().sort, post.attachList.any().attach.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> getPostPagePath() {
      log.info("PostDocs.getPostPagePath");

      return PrintUtil
          .builder()

          .parameter("blogId", "블로그 일련 번호")

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getPostPage() {
      log.info("PostDocs.getPostPage");

      return PrintUtil
          .builder()

          .parameter(FBlogSearch.page, FBlogSearch.size)

          .prefixOptional()
          .linkParameter("blog-docs-get-post-page-key", FBlogSearch.key)
          .parameter(FBlogSearch.cursor, FBlogSearch.word)

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getPost() {
      log.info("PostDocs.getPost");

      return PrintUtil
          .builder()

          .parameter("blogId", "블로그 일련 번호")

          .prefixDescription("포스팅")

          .parameter(post.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> updatePostPath() {
      log.info("PostDocs.updatePostPath");

      return PrintUtil
          .builder()

          .parameter("blogId", "블로그 일련 번호")

          .prefixDescription("포스팅")

          .parameter(post.id)

          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> updatePost() {
      log.info("PostDocs.updatePost");

      return PrintUtil
          .builder()

          .prefixDescription("포스팅")

          .field(post.title, post.contents, post.useFlag)

          .prefixOptional()

          .prefixDescription("해시태그")
          .field(post.hashtagList.any().sort, post.hashtagList.any().hashtag.id)

          .prefixDescription("첨부 파일")
          .field(post.attachList.any().sort, post.attachList.any().attach.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> removePost() {
      log.info("PostDocs.removePost");

      return PrintUtil
          .builder()

          .parameter("blogId", "블로그 일련 번호")

          .prefixDescription("포스팅")

          .parameter(post.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> hasPermissionForPost() {
      log.info("PostDocs.hasPermissionForPost");

      return PrintUtil
          .builder()

          .prefixDescription("포스팅")

          .parameter(post.id)

          .build()
          .getParameterList();
    }
  }

  public static class Response {

    public static List<FieldDescriptor> createPost() {
      log.info("PostDocs.createPost");

      return ResponseDocs
          .data()

          .prefixDescription("포스팅")

          .field(post.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getPostPage() {
      log.info("PostDocs.getPostPage");

      return ResponseDocs
          .page()

          .prefixDescription("포스팅")

          .field(post.id, post.title, post.createAt, post.updateAt)

          .addField(SignDocs.Docs.setAuditor("page.content[].creator",
              "작성자", false))
          .addField(SignDocs.Docs.setAuditor("page.content[].updater",
              "마지막 수정자", false))

          .prefixOptional()

          .prefixDescription("해시태그")

          .field(post.hashtagList.any().sort)
          .addField(HashtagDocs.Docs.setHashtag("page.content[].hashtagList[].hashtag",
              "해시태그", true))

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getPost() {
      log.info("PostDocs.getPost");

      return ResponseDocs
          .data()

          .prefixDescription("포스팅")

          .field(post.id, post.title, post.contents, post.createAt, post.updateAt)

          .addField(SignDocs.Docs.setAuditor("data.creator",
              "작성자", false))
          .addField(SignDocs.Docs.setAuditor("data.updater",
              "마지막 수정자", false))

          .prefixOptional()

          .prefixDescription("해시태그")

          .field(post.hashtagList.any().sort)
          .addField(HashtagDocs.Docs.setHashtag("data.hashtagList[].hashtag",
              "해시태그", true))

          .prefixDescription("첨부 파일")

          .field(post.attachList.any().sort)
          .addField(AttachDocs.Docs
              .setAttach("data.attach", "첨부 파일", true))

          .clear()
          .build()
          .getFieldList();
    }
  }

  public static class Docs {

  }

}
