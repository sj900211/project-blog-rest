package run.freshr.common.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Table comment.
 *
 * @author FreshR
 * @apiNote Spring boot 2.7.7 버전에 종속된 hibernate 버전에서는<br>
 * Comment 어노테이션을 클래스 단위에서 사용할 수 없다.<br>
 * Spring boot 버전을 올리면 java EE 가 jakarta EE 로 전환되는데<br>
 * QueryDSL 이 아직 jakarta EE 전환되도록 수정된 버전이 없어<br>
 * 설정에서 오류가 발생하게 된다.<br>
 * 그래서 생성하게된 Annotation<br>
 * 이후 QueryDsl 이 jakarta 를 지원하게 되어<br>
 * spring boot 버전을 업그레이드 한다면 제거 예정.<br>
 * 사용 유무에 따른 변화는 없음. 아무 기능이 없음<br>
 * @since 2022. 12. 23. 오후 4:08:48
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface TableComment {

  /**
   * Value string.
   *
   * @return string
   * @apiNote 설명
   * @author FreshR
   * @since 2022. 12. 23. 오후 4:08:48
   */
  String value();

}
