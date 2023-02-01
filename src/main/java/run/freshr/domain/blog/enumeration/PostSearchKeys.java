package run.freshr.domain.blog.enumeration;

import static java.util.Arrays.stream;
import static java.util.List.of;
import static run.freshr.common.utils.QueryUtil.searchKeyword;
import static run.freshr.domain.blog.entity.QPost.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import run.freshr.common.extension.enumeration.SearchEnumExtension;

@Slf4j
public enum PostSearchKeys implements SearchEnumExtension {

  ALL("전체", of(post.title, post.contents, post.creator.username)),
  TITLE("제목", of(post.title)),
  CONTENTS("내용", of(post.contents)),
  CREATOR("작성자 이름", of(post.creator.name));

  private final String value;
  private final List<StringPath> paths;

  PostSearchKeys(String value, List<StringPath> paths) {
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

  public static PostSearchKeys find(String key) {
    log.info("BlogSearchKeys.find");

    return stream(PostSearchKeys.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(ALL);
  }

}
