package run.freshr.common.utils;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.PageRequest.of;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import run.freshr.common.data.CursorData;
import run.freshr.common.extension.enumeration.SearchEnumExtension;
import run.freshr.common.extension.request.SearchExtension;
import run.freshr.common.functional.PagingFunctional;
import run.freshr.common.functional.SearchEnumFunctional;
import run.freshr.common.functional.SearchKeywordFunctional;

/**
 * QueryDsl 공통 기능 정의.
 *
 * @author FreshR
 * @apiNote QueryDsl 공통 기능 정의
 * @since 2023. 1. 12. 오후 7:14:00
 */
@Slf4j
@Component
public class QueryUtil {

  /**
   * 자연어 검색.
   *
   * @param word  word
   * @param paths paths
   * @return boolean builder
   * @apiNote 자연어 검색
   * @author FreshR
   * @since 2023. 1. 12. 오후 7:14:00
   */
  public static BooleanBuilder searchKeyword(String word, List<StringPath> paths) {
    log.info("QueryUtil.searchKeyword");

    final SearchKeywordFunctional SEARCH_KEYWORD_FUNCTIONAL = (functionalWord, functionalPaths) -> {
      BooleanBuilder booleanBuilder = new BooleanBuilder();

      functionalPaths.forEach(path -> booleanBuilder.or(path.containsIgnoreCase(functionalWord)));

      return booleanBuilder;
    };

    return SEARCH_KEYWORD_FUNCTIONAL.search(word, paths);
  }

  /**
   * Enum 자연어 검색.
   *
   * @param <E>         type parameter
   * @param word        word
   * @param enumeration enumeration
   * @return boolean builder
   * @apiNote api note
   * @author FreshR
   * @since 2023. 1. 12. 오후 7:14:00
   */
  public static <E extends SearchEnumExtension> BooleanBuilder searchEnum
      (String word, E enumeration) {
    log.info("QueryUtil.searchEnum");

    final SearchEnumFunctional<E> SEARCH_ENUM_FUNCTIONAL =
        (functionalWord, functionalEnumeration) -> functionalEnumeration.search(functionalWord);

    return SEARCH_ENUM_FUNCTIONAL.search(word, enumeration);
  }

  /**
   * 페이징 처리
   *
   * @param <E>    type parameter
   * @param <Q>    type parameter
   * @param <S>    type parameter
   * @param query  query
   * @param path   path
   * @param search search
   * @return page
   * @apiNote 페이징 처리
   * @author FreshR
   * @since 2023. 1. 19. 오후 6:40:17
   */
  public static <E, Q extends EntityPathBase<E>, S extends SearchExtension<?>> Page<E> paging
  (JPAQuery<E> query, Q path, S search) {
    log.info("GlobalFunctional.paging");

    return paging(query, path, search, null);
  }

  /**
   * 페이징 처리
   *
   * @param <E>       type parameter
   * @param <Q>       type parameter
   * @param <S>       type parameter
   * @param query     query
   * @param path      path
   * @param search    search
   * @param orderList order list
   * @return page
   * @apiNote 페이징 처리
   * @author FreshR
   * @since 2023. 1. 19. 오후 6:40:33
   */
  public static <E, Q extends EntityPathBase<E>, S extends SearchExtension<?>> Page<E> paging
  (JPAQuery<E> query, Q path, S search, List<OrderSpecifier<?>> orderList) {
    log.info("GlobalFunctional.paging");

    Long cursor = ofNullable(search.getCursor())
        .orElse(LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli());

    final PagingFunctional<E, S> PAGING_FUNCTIONAL = (functionalQuery, functionalSearch) -> {
      PageRequest pageRequest =
          of(functionalSearch.getPage() - 1, functionalSearch.getSize());

      Long totalCount = functionalQuery.select(Wildcard.count).fetchOne();

      functionalQuery.select(path);
      functionalQuery.offset(pageRequest.getOffset())
          .limit(pageRequest.getPageSize());

      if (!isNull(orderList) && !orderList.isEmpty()) {
        functionalQuery.orderBy(orderList.toArray(new OrderSpecifier<?>[0]));
      }

      List<E> result = functionalQuery.fetch();

      return new CursorData<>(result, pageRequest,
          ofNullable(totalCount).orElse(0L), cursor);
    };

    return PAGING_FUNCTIONAL.paging(query, search);
  }

}
