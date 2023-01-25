package run.freshr.domain.community.enumeration;

import static java.util.Arrays.stream;
import static java.util.List.of;
import static run.freshr.common.utils.QueryUtil.searchKeyword;
import static run.freshr.domain.community.entity.QBoardNotice.boardNotice;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import run.freshr.common.extension.enumeration.SearchEnumExtension;

@Slf4j
public enum BoardNoticeSearchKeys implements SearchEnumExtension {

  ALL("전체", of(boardNotice.title, boardNotice.contents, boardNotice.creator.username)),
  TITLE("제목", of(boardNotice.title)),
  CONTENTS("내용", of(boardNotice.contents)),
  CREATOR("작성자 이름", of(boardNotice.creator.name));

  private final String value;
  private final List<StringPath> paths;

  BoardNoticeSearchKeys(String value, List<StringPath> paths) {
    this.value = value;
    this.paths = paths;
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return value;
  }

  public List<StringPath> getPaths() {
    return paths;
  }

  @Override
  public BooleanBuilder search(String word) {
    return searchKeyword(word, paths);
  }

  public static BoardNoticeSearchKeys find(String key) {
    log.info("BoardNoticeSearchKeys.find");

    return stream(BoardNoticeSearchKeys.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(ALL);
  }

}
