package run.freshr.domain.auth;

import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static run.freshr.domain.auth.entity.QAccount.account;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;

@Slf4j
public class AccountDocs {

  public static class Request {
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
  }

  public static class Response {
    public static List<FieldDescriptor> getInfo() {
      log.info("AccountDocs.Response.getInfo");

      return ResponseDocs
          .Response
          .data()

          .prefixDescription("중간 관리자")
          .field(account.id, account.username, account.name, account.useFlag)
          .linkField(account.privilege)

          .prefixOptional()
          .field(account.signAt, account.removeAt, account.createAt, account.updateAt)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {
  }

}
