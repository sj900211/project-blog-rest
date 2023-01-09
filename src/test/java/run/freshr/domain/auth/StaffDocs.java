package run.freshr.domain.auth;

import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static run.freshr.domain.auth.entity.QStaff.staff;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;

@Slf4j
public class StaffDocs {

  public static class Request {
    public static List<FieldDescriptor> updateInfo() {
      log.info("StaffDocs.Request.updateInfo");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .prefixDescription("관리자")
          .field(staff.name, "이름 [RSA 암호화]")

          .build()
          .getFieldList();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> getInfo() {
      log.info("StaffDocs.Response.getInfo");

      return ResponseDocs
          .Response
          .data()

          .prefixDescription("중간 관리자")
          .field(staff.id, staff.username, staff.name, staff.useFlag)
          .linkField(staff.privilege)

          .prefixOptional()
          .field(staff.signAt, staff.removeAt, staff.createAt, staff.updateAt)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {
  }

}
