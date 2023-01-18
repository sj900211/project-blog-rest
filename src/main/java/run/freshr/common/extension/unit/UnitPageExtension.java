package run.freshr.common.extension.unit;

import org.springframework.data.domain.Page;
import run.freshr.common.extension.request.SearchExtension;

/**
 * Paging 조회가 있는 Unit 공통 기능을 정의
 *
 * @param <E>  Entity
 * @param <ID> ID 데이터 유형
 * @param <S>  SearchExtension 을 상속받은 Get Parameter VO
 * @author FreshR
 * @apiNote Paging 조회가 있는 Unit 공통 기능을 정의
 * @since 2023. 1. 12. 오후 6:43:27
 */
public interface UnitPageExtension<E, ID, S extends SearchExtension<ID>>
    extends UnitDefaultExtension<E, ID> {

  /**
   * 페이징 데이터 조회.
   *
   * @param search search
   * @return page
   * @apiNote 페이징 데이터 조회
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:43:27
   */
  Page<E> getPage(S search);

}
