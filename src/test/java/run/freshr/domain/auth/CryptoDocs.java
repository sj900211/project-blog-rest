package run.freshr.domain.auth;

import static org.springframework.restdocs.payload.JsonFieldType.STRING;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;

@Slf4j
public class CryptoDocs {

  public static class Request {

    public static List<FieldDescriptor> getEncryptRsa() {
      log.info("CryptoDocs.Request.getEncryptRsa");

      return PrintUtil.builder()

          .field("rsa", "BASE64 로 인코딩된 RSA 공개키", STRING)
          .field("plain", "암호화할 평문", STRING)

          .build()
          .getFieldList();
    }
  }

  public static class Response {

    public static List<FieldDescriptor> getPublicKey() {
      log.info("CryptoDocs.Request.getPublicKey");

      return ResponseDocs
          .data()

          .field("key", "BASE64 로 인코딩된 RSA 공개키", STRING)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getEncryptRsa() {
      log.info("CryptoDocs.Response.getEncryptRsa");

      return ResponseDocs
          .data()

          .field("encrypt", "RSA 암호화 문자", STRING)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {

  }

}
