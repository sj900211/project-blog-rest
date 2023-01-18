package run.freshr.domain.auth;

import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static run.freshr.domain.auth.entity.QManager.manager;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;

@Slf4j
public class ManagerDocs {

  public static class Request {
    public static List<FieldDescriptor> updateInfo() {
      log.info("ManagerDocs.Response.updateInfo");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .prefixDescription("관리자")
          .field(manager.name, "이름 [RSA 암호화]")

          .build()
          .getFieldList();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> getInfo() {
      log.info("ManagerDocs.Response.getInfo");

      return ResponseDocs
          .data()

          .prefixDescription("관리자")
          .field(manager.id, manager.username, manager.name, manager.useFlag)
          .linkField("manager-docs-get-info-privilege", manager.privilege)

          .prefixOptional()
          .field(manager.signAt, manager.removeAt, manager.createAt, manager.updateAt)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {
  }

}
