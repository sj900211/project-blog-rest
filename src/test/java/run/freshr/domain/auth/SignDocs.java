package run.freshr.domain.auth;

import static io.jsonwebtoken.lang.Strings.hasLength;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static run.freshr.domain.auth.entity.QAccount.account;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;
import run.freshr.common.util.PrintUtil.Builder;

@Slf4j
public class SignDocs {

  public static class Request {

    public static List<FieldDescriptor> signIn() {
      log.info("SignDocs.Request.signIn");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .prefixDescription("계정")

          .field(account.username, "고유 아이디 [RSA 암호화]")
          .field(account.password, "비밀번호 [RSA 암호화]")

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
          .field(account.password, "비밀번호 [RSA 암호화]")

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> updateInfo() {
      log.info("AccountDocs.Request.updateInfo");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .prefixDescription("관리자")
          .field(account.name, "이름 [RSA 암호화]")

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

    public static List<FieldDescriptor> getInfo() {
      log.info("AccountDocs.Response.getInfo");

      return ResponseDocs
          .data()

          .prefixDescription("중간 관리자")
          .field(account.id, account.username, account.name, account.useFlag)
          .linkField("account-docs-get-info-privilege", account.privilege)

          .prefixOptional()
          .field(account.signAt, account.removeAt, account.createAt, account.updateAt)

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

    public static List<FieldDescriptor> setAuditor(String prefix, String description,
        Boolean optional) {
      log.info("ResponseDocs.setAuditor");

      Builder builder = PrintUtil.builder();

      if (hasLength(prefix)) {
        builder.prefix(prefix);
      }

      if (hasLength(description)) {
        builder.prefixDescription(prefix);
      }

      builder.prefixOptional(optional);

      return builder
          .field(account.id, account.privilege, account.username)
          .field("name", "이름", STRING)
          .build()
          .getFieldList();
    }
  }

}
