package run.freshr.common.extension.unit;

/**
 * NoSQL 의 경우 Unit 공통 기능을 정의.
 *
 * @param <D>  Document OR RedisHash
 * @param <ID> ID 데이터 유형
 * @author FreshR
 * @apiNote NoSQL 의 경우 Unit 공통 기능을 정의
 * @since 2023. 1. 12. 오후 6:35:32
 */
public interface UnitDocumentDefaultExtension<D, ID> {

  /**
   * 데이터 생성.
   *
   * @param document document
   * @apiNote 데이터 생성
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:35:32
   */
  void save(D document);

  /**
   * 데이터 존재 여부.
   *
   * @param id id
   * @return boolean
   * @apiNote 데이터 존재 여부
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:35:32
   */
  Boolean exists(ID id);

  /**
   * 데이터 조회.
   *
   * @param id id
   * @return d
   * @apiNote 데이터 조회
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:35:32
   */
  D get(ID id);

  /**
   * 데이터 삭제.
   *
   * @param id id
   * @apiNote 데이터 삭제
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:35:32
   */
  void delete(ID id);

}
