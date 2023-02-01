package run.freshr.domain.blog.enumeration;

import static java.util.Arrays.stream;
import static java.util.List.of;
import static run.freshr.common.utils.QueryUtil.searchKeyword;
import static run.freshr.domain.blog.entity.QBlog.blog;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import run.freshr.common.extension.enumeration.SearchEnumExtension;

@Slf4j
public enum BlogSearchKeys implements SearchEnumExtension {

  ALL("전체", of(blog.title, blog.creator.username)),
  TITLE("제목", of(blog.title)),
  CREATOR("작성자 이름", of(blog.creator.name));

  private final String value;
  private final List<StringPath> paths;

  BlogSearchKeys(String value, List<StringPath> paths) {
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

  public static BlogSearchKeys find(String key) {
    log.info("BlogSearchKeys.find");

    return stream(BlogSearchKeys.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(ALL);
  }

}
