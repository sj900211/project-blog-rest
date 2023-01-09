package run.freshr.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Query dsl config.
 *
 * @author FreshR
 * @apiNote JPAQuery 를 프로젝트 전역에서 주입받을 수 있도록 설정
 * @since 2022. 12. 23. 오후 3:39:25
 */
@Configuration
public class QueryDslConfig {

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

}
