package run.freshr.common.routing;

import static io.lettuce.core.models.role.RedisInstance.Role.MASTER;
import static io.lettuce.core.models.role.RedisInstance.Role.REPLICA;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Profile({"dev", "staging", "prod"})
@Slf4j
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? REPLICA : MASTER;
  }

}
