package run.freshr.domain.community;

import static run.freshr.domain.community.entity.QBoardNotice.boardNotice;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;
import run.freshr.domain.auth.SignDocs;
import run.freshr.domain.common.AttachDocs;
import run.freshr.domain.community.vo.FCommunitySearch;

@Slf4j
public class BoardNoticeDocs {

  public static class Request {

    public static List<FieldDescriptor> createBoardNotice() {
      log.info("BoardNoticeDocs.createBoardNotice");

      return PrintUtil
          .builder()

          .prefixDescription("게시글")

          .field(boardNotice.title, boardNotice.contents, boardNotice.fixed, boardNotice.expose)

          .prefixOptional()
          .prefixDescription("첨부 파일")
          .field(boardNotice.attachList.any().sort, boardNotice.attachList.any().attach.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> getBoardNoticePage() {
      log.info("BoardNoticeDocs.getBoardNoticePage");

      return PrintUtil
          .builder()

          .parameter(FCommunitySearch.page, FCommunitySearch.size)

          .prefixOptional()
          .linkParameter("board-notice-docs-get-board-notice-page-key", FCommunitySearch.key)
          .parameter(FCommunitySearch.word,
              FCommunitySearch.startDate, FCommunitySearch.endDate,
              FCommunitySearch.orderType, FCommunitySearch.orderBy)

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getBoardNotice() {
      log.info("BoardNoticeDocs.getBoardNotice");

      return PrintUtil
          .builder()

          .prefixDescription("게시글")

          .parameter(boardNotice.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> updateBoardNoticePath() {
      log.info("BoardNoticeDocs.updateBoardNoticePath");

      return PrintUtil
          .builder()

          .prefixDescription("게시글")

          .parameter(boardNotice.id)

          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> updateBoardNotice() {
      log.info("BoardNoticeDocs.updateBoardNotice");

      return PrintUtil
          .builder()

          .prefixDescription("게시글")

          .field(boardNotice.title, boardNotice.contents, boardNotice.fixed, boardNotice.expose)

          .prefixOptional()
          .prefixDescription("첨부 파일")
          .field(boardNotice.attachList.any().sort, boardNotice.attachList.any().attach.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> removeBoardNotice() {
      log.info("BoardNoticeDocs.removeBoardNotice");

      return PrintUtil
          .builder()

          .prefixDescription("게시글")

          .parameter(boardNotice.id)

          .build()
          .getParameterList();
    }
  }

  public static class Response {

    public static List<FieldDescriptor> createBoardNotice() {
      log.info("BoardNoticeDocs.createBoardNotice");

      return ResponseDocs
          .data()

          .prefixDescription("게시글")

          .field(boardNotice.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getBoardNoticePage() {
      log.info("BoardNoticeDocs.getBoardNoticePage");

      return ResponseDocs
          .page()

          .prefixDescription("게시글")

          .field(boardNotice.id, boardNotice.title, boardNotice.fixed, boardNotice.expose,
              boardNotice.views, boardNotice.createAt, boardNotice.updateAt)

          .addField(SignDocs.Docs
              .setAuditor("page.content[].creator", "작성자", false))
          .addField(SignDocs.Docs
              .setAuditor("page.content[].updater", "작성자", false))

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getBoardNotice() {
      log.info("BoardNoticeDocs.getBoardNotice");

      return ResponseDocs
          .data()

          .prefixDescription("게시글")

          .field(boardNotice.id, boardNotice.title, boardNotice.contents, boardNotice.fixed,
              boardNotice.expose, boardNotice.views, boardNotice.createAt, boardNotice.updateAt)

          .addField(SignDocs.Docs
              .setAuditor("data.creator", "작성자", false))
          .addField(SignDocs.Docs
              .setAuditor("data.updater", "작성자", false))
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
