package run.freshr.domain.common;

import static io.jsonwebtoken.lang.Strings.hasLength;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static run.freshr.domain.blog.entity.QBlog.blog;
import static run.freshr.domain.blog.entity.QPost.post;
import static run.freshr.domain.common.entity.QHashtag.hashtag;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;
import run.freshr.common.util.PrintUtil.Builder;
import run.freshr.domain.auth.SignDocs;
import run.freshr.domain.common.vo.FCommonSearch;

@Slf4j
public class HashtagDocs {

  public static class Request {

    public static List<ParameterDescriptor> searchHashtag() {
      log.info("HashtagDocs.searchHashtag");

      return PrintUtil
          .builder()

          .parameter("keyword", "검색어")

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getBlogPageByHashtagPath() {
      log.info("HashtagDocs.getBlogPageByHashtagPath");

      return PrintUtil
          .builder()

          .parameter("keyword", "검색어")

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getBlogPageByHashtag() {
      log.info("HashtagDocs.getBlogPageByHashtag");

      return PrintUtil
          .builder()

          .parameter(FCommonSearch.page, FCommonSearch.size)

          .prefixOptional()
          .parameter(FCommonSearch.cursor)

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getPostPageByHashtagPath() {
      log.info("HashtagDocs.getPostPageByHashtagPath");

      return PrintUtil
          .builder()

          .parameter("keyword", "검색어")

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getPostPageByHashtag() {
      log.info("HashtagDocs.getPostPageByHashtag");

      return PrintUtil
          .builder()

          .parameter(FCommonSearch.page, FCommonSearch.size)

          .prefixOptional()
          .parameter(FCommonSearch.cursor)

          .clear()
          .build()
          .getParameterList();
    }
  }

  public static class Response {

    public static List<FieldDescriptor> getHashtagStatistics() {
      log.info("HashtagDocs.Response.getHashtagStatistics");

      return ResponseDocs
          .list()

          .prefixDescription("해시태그")

          .field(hashtag.id)
          .field("blogCount", "블로그 수", NUMBER)
          .field("postCount", "포스팅 수", NUMBER)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> searchHashtag() {
      log.info("HashtagDocs.Response.searchHashtag");

      return ResponseDocs
          .list()

          .prefixDescription("해시태그")

          .field(hashtag.id)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getBlogPageByHashtag() {
      log.info("HashtagDocs.Response.getBlogPageByHashtag");

      return ResponseDocs
          .page()

          .prefixDescription("블로그")

          .field(blog.id, blog.title, blog.viewType, blog.description,
              blog.createAt, blog.updateAt)

          .addField(SignDocs.Docs.setAuditor("page.content[].creator",
              "작성자", false))
          .addField(SignDocs.Docs.setAuditor("page.content[].updater",
              "마지막 수정자", false))

          .prefixDescription("해시태그")
          .field(blog.hashtagList.any().sort)
          .addField(HashtagDocs.Docs.setHashtag("page.content[].hashtagList[].hashtag",
              "해시태그", false))

          .addField(AttachDocs.Docs.setAttach("page.content[].cover",
              "커버 이미지", true))

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getPostPageByHashtag() {
      log.info("HashtagDocs.Response.getPostPageByHashtag");

      return ResponseDocs
          .page()

          .prefixDescription("포스팅")

          .field(post.id, post.title, post.createAt, post.updateAt)

          .addField(SignDocs.Docs.setAuditor("page.content[].creator",
              "작성자", false))
          .addField(SignDocs.Docs.setAuditor("page.content[].updater",
              "마지막 수정자", false))

          .prefixDescription("해시태그")
          .field(post.hashtagList.any().sort)
          .addField(HashtagDocs.Docs.setHashtag("page.content[].hashtagList[].hashtag",
              "해시태그", false))

          .build()
          .getFieldList();
    }
  }

  public static class Docs {

    public static List<FieldDescriptor> setHashtag(String prefix, String description,
        Boolean optional) {
      log.info("HashtagDocs.Docs.setHashtag");

      Builder builder = PrintUtil.builder();

      if (hasLength(prefix)) {
        builder.prefix(prefix);
      }

      if (hasLength(description)) {
        builder.prefixDescription(description);
      }

      builder.prefixOptional(optional);

      return builder
          .field(hashtag.id)
          .build()
          .getFieldList();
    }
  }

}
