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
public interface UnitDefaultExtension<E, ID> {

  /**
   * 데이터 생성
   *
   * @param entity entity
   * @return id
   * @apiNote 데이터 생성
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:30:08
   */
  ID create(E entity);

}
