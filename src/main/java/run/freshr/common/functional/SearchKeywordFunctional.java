package run.freshr.common.functional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;

/**
 * 자연어 검색 함수형 인터페이스 정의.
 *
 * @author FreshR
 * @apiNote 자연어 검색 함수형 인터페이스 정의
 * @since 2023. 1. 12. 오후 6:50:39
 */
@FunctionalInterface
public interface SearchKeywordFunctional {

  /**
   * 자연어 검색.
   *
   * @param word  word
   * @param paths paths
   * @return boolean builder
   * @apiNote 자연어 검색
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:50:39
   */
  BooleanBuilder search(String word, List<StringPath> paths);

}
