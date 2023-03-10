package run.freshr.domain.common;

import static io.jsonwebtoken.lang.Strings.hasLength;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static run.freshr.domain.common.entity.QAttach.attach;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestPartDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;
import run.freshr.common.util.PrintUtil.Builder;

@Slf4j
public class AttachDocs {

  public static class Request {
    public static RequestPartDescriptor createAttachFile() {
      log.info("AttachDocs.Request.createAttachFile");

      return partWithName("files").description("첨부 파일");
    }

    public static List<ParameterDescriptor> createAttach() {
      log.info("AttachDocs.Request.createAttach");

      return PrintUtil
          .builder()

          .prefixDescription("첨부 파일")

          .prefixOptional()

          .parameter("directory", "저장될 디렉토리 이름")
          .parameter(attach.alt, attach.title)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> existAttach() {
      log.info("AttachDocs.Request.existAttach");

      return PrintUtil
          .builder()

          .prefixDescription("첨부 파일")

          .parameter(attach.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getAttach() {
      log.info("AttachDocs.Request.getAttach");

      return PrintUtil
          .builder()

          .prefixDescription("첨부 파일")

          .parameter(attach.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> removeAttach() {
      log.info("AttachDocs.Request.removeAttach");

      return PrintUtil
          .builder()

          .prefixDescription("첨부 파일")

          .parameter(attach.id)

          .build()
          .getParameterList();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> createAttach() {
      log.info("AttachDocs.Response.createAttach");

      return ResponseDocs
          .list()

          .prefixDescription("첨부 파일")

          .field(attach.id)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> existAttach() {
      log.info("AttachDocs.Response.existAttach");

      return ResponseDocs
          .data()

          .prefixDescription("첨부 파일")

          .field("is", "존재 여부", BOOLEAN)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAttach() {
      log.info("AttachDocs.Response.getAttach");

      return ResponseDocs
          .data()

          .prefixDescription("첨부 파일")

          .field("url", "리소스 URL <만료 시간 1 분>", STRING)
          .field(
              attach.id,
              attach.contentType,
              attach.filename, attach.size,
              attach.createAt, attach.updateAt
          )

          .prefixOptional()

          .field(attach.alt, attach.title)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {
    public static List<FieldDescriptor> setAttach(String prefix, String description,
        Boolean optional) {
      log.info("ResponseDocs.setAttach");

      Builder builder = PrintUtil.builder();

      if (hasLength(prefix)) {
        builder.prefix(prefix);
      }

      if (hasLength(description)) {
        builder.prefixDescription(prefix);
      }

      builder.prefixOptional(optional);

      return builder
          .field(attach.id, attach.contentType, attach.filename, attach.size, attach.alt,
              attach.title, attach.createAt, attach.updateAt)
          .field("url", "리소스 URL", STRING)
          .build()
          .getFieldList();
    }
  }

}
