package run.freshr.common.routing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static io.lettuce.core.models.role.RedisInstance.Role.MASTER;
import static io.lettuce.core.models.role.RedisInstance.Role.REPLICA;

/**
 * Replication Datasource 연결 설정.
 *
 * @author FreshR
 * @apiNote Replication Datasource 연결 설정
 * @since 2023. 1. 12. 오후 6:52:49
 */
@Profile({"dev", "staging", "prod"})
@Slf4j
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

  /**
   * Datasource 결정.
   *
   * @return object
   * @apiNote Transactional 에 따라 Datasource 를 결정
   * @author FreshR
   * @since 2023. 1. 12. 오후 6:52:49
   */
  @Override
  protected Object determineCurrentLookupKey() {
    return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? REPLICA : MASTER;
  }

}
