package run.freshr.common.extension.unit;

/**
 * Unit 공통 기능을 정의
 *
 * @param <E>  Entity
 * @param <ID> ID 데이터 유형
 * @author FreshR
 * @apiNote Unit 공통 기능을 정의
 * @since 2023. 1. 12. 오후 6:30:08
 */
public interface UnitGetExtension<E, ID> extends UnitDefaultExtension<E, ID> {

  /**
   * 데이터 존재 여부.
   *
   * @param id id
   * @return boolean
   * @apiNote 데이터 존재 여부
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:30:08
   */
  Boolean exists(ID id);

  /**
   * 데이터 조회.
   *
   * @param id id
   * @return e
   * @apiNote 데이터 조회
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:30:08
   */
  E get(ID id);

}
