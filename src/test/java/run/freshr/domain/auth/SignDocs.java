package run.freshr.domain.auth;

import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static run.freshr.domain.auth.entity.QSign.sign;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;

@Slf4j
public class SignDocs {

  public static class Request {
    public static List<FieldDescriptor> signIn() {
      log.info("SignDocs.Request.signIn");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .prefixDescription("계정")

          .field(sign.username, "고유 아이디 [RSA 암호화]")
          .field(sign.password, "비밀번호 [RSA 암호화]")

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> updatePassword() {
      log.info("SignDocs.Request.updatePassword");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .prefixDescription("계정")

          .field("originPassword", "기존 비밀번호 [RSA 암호화]", STRING)
          .field(sign.password, "비밀번호 [RSA 암호화]")

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> refreshToken() {
      log.info("SignDocs.Request.refreshToken");

      return PrintUtil
          .builder()
          .field("refreshToken", "Refresh 토큰", STRING)

          .build()
          .getFieldList();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> signIn() {
      log.info("SignDocs.Response.signIn");

      return ResponseDocs
          .data()

          .field("accessToken", "접속 토큰", STRING)
          .field("refreshToken", "갱신 토큰", STRING)

          .build()
          .getFieldList();
    }
    public static List<FieldDescriptor> refreshToken() {
      log.info("SignDocs.Response.refreshToken");

      return ResponseDocs
          .data()

          .field("accessToken", "접속 토큰", STRING)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {
  }

}
