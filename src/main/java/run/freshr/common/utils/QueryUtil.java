package run.freshr.common.utils;

import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.PageRequest.of;

import com.querydsl.core.BooleanBuilder;
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

@Slf4j
@Component
public class QueryUtil {

  public static BooleanBuilder searchKeyword(String word, List<StringPath> paths) {
    log.info("QueryUtil.searchKeyword");

    final SearchKeywordFunctional SEARCH_KEYWORD_FUNCTIONAL = (functionalWord, functionalPaths) -> {
      BooleanBuilder booleanBuilder = new BooleanBuilder();

      functionalPaths.forEach(path -> booleanBuilder.or(path.containsIgnoreCase(functionalWord)));

      return booleanBuilder;
    };

    return SEARCH_KEYWORD_FUNCTIONAL.search(word, paths);
  }

  public static <E extends SearchEnumExtension> BooleanBuilder searchEnum
      (String word, E enumeration) {
    log.info("QueryUtil.searchEnum");

    final SearchEnumFunctional<E> SEARCH_ENUM_FUNCTIONAL =
        (functionalWord, functionalEnumeration) -> functionalEnumeration.search(functionalWord);

    return SEARCH_ENUM_FUNCTIONAL.search(word, enumeration);
  }

  public static <E, Q extends EntityPathBase<E>, S extends SearchExtension<?>> Page<E> paging
      (JPAQuery<E> query, Q path, S search) {
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

      List<E> result = functionalQuery.fetch();

      return new CursorData<>(result, pageRequest,
          ofNullable(totalCount).orElse(0L), cursor);
    };

    return PAGING_FUNCTIONAL.paging(query, search);
  }

}
