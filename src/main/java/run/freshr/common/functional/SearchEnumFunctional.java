package run.freshr.common.functional;

import com.querydsl.core.BooleanBuilder;
import run.freshr.common.extension.enumeration.SearchEnumExtension;

/**
 * SearchEnumExtension 을 사용한 자연어 검색 함수형 인터페이스 정의.
 *
 * @param <E> SearchEnumExtension 상속받은 검색 유형 Enum
 * @author FreshR
 * @apiNote SearchEnumExtension 을 사용한 자연어 검색 함수형 인터페이스 정의
 * @since 2023. 1. 12. 오후 6:46:00
 */
@FunctionalInterface
public interface SearchEnumFunctional<E extends SearchEnumExtension> {

  /**
   * 자연어 검색.
   *
   * @param word        word
   * @param enumeration enumeration
   * @return boolean builder
   * @apiNote 자연어 검색
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:46:00
   */
  BooleanBuilder search(String word, E enumeration);

}
