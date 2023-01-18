package run.freshr.common.functional;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import run.freshr.common.extension.request.SearchExtension;

/**
 * 페이징 함수형 인터페이스 정의.
 *
 * @param <E> Entity
 * @param <S> SearchExtension 을 상속받은 Get Parameter VO
 * @author FreshR
 * @apiNote 페이징 함수형 인터페이스 정의
 * @since 2023. 1. 12. 오후 6:44:52
 */
@FunctionalInterface
public interface PagingFunctional<E, S extends SearchExtension<?>> {

  /**
   * 데이터 페이징 처리.
   *
   * @param query  query
   * @param search search
   * @return page
   * @apiNote 데이터 페이징 처리
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:44:52
   */
  Page<E> paging(JPAQuery<E> query, S search);

}
