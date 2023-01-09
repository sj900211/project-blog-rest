package run.freshr.domain.auth.enumeration;

import static java.util.Arrays.stream;
import static java.util.List.of;
import static run.freshr.common.utils.QueryUtil.searchKeyword;
import static run.freshr.domain.auth.entity.QAccount.account;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import run.freshr.common.extension.enumeration.SearchEnumExtension;

@Slf4j
public enum AccountSearchKeys implements SearchEnumExtension {

  ALL("전체", of(account.username, account.name)),
  USERNAME("아이디", of(account.username)),
  NAME("이름", of(account.name));

  private final String value;
  private final List<StringPath> paths;

  AccountSearchKeys(String value, List<StringPath> paths) {
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

  public static AccountSearchKeys find(String key) {
    log.info("AccountSearchKeys.find");

    return stream(AccountSearchKeys.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(ALL);
  }

}
