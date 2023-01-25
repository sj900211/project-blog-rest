package run.freshr.common.config;

import static io.lettuce.core.models.role.RedisInstance.Role.MASTER;
import static io.lettuce.core.models.role.RedisInstance.Role.REPLICA;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import run.freshr.common.routing.ReplicationRoutingDataSource;

/**
 * DataSource 설정.
 *
 * @author FreshR
 * @apiNote Replication Database 연결 설정
 * @since 2023. 1. 12. 오후 5:52:51
 */
@Profile({"dev", "staging", "prod"})
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class RoutingDataSourceConfig {

  /**
   * Main Database 설정
   *
   * @return data source
   * @apiNote Main Database 설정
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:52:51
   */
  @Bean(name = "dataSourceMain")
  @ConfigurationProperties(prefix="spring.datasource.main.hikari")
  public DataSource dataSourceMain() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  /**
   * Replica Database 설정
   *
   * @return data source
   * @apiNote Replica Database 설정
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:52:51
   */
  @Bean(name = "dataSourceReplica")
  @ConfigurationProperties(prefix="spring.datasource.replica.hikari")
  public DataSource dataSourceReplica() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  /**
   * RoutingDataSource 설정.
   *
   * @param dataSourceMain    data source main
   * @param dataSourceReplica data source replica
   * @return data source
   * @apiNote AbstractRoutingDataSource 에 DataSource 정보를 설정
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:52:51
   */
  @Bean(name = "routingDataSource")
  public DataSource routingDataSource(
      @Qualifier("dataSourceMain") final DataSource dataSourceMain,
      @Qualifier("dataSourceReplica") final DataSource dataSourceReplica) {
    ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
    Map<Object, Object> dataSourceMap = new HashMap<>();

    dataSourceMap.put(MASTER, dataSourceMain);
    dataSourceMap.put(REPLICA, dataSourceReplica);

    routingDataSource.setTargetDataSources(dataSourceMap);
    routingDataSource.setDefaultTargetDataSource(dataSourceMain);

    return routingDataSource;
  }

  /**
   * DataSource 선택 설정.
   *
   * @param routingDataSource routing data source
   * @return data source
   * @apiNote 기존의 DataSource 는 Transaction 이 시작되는 시점에서<br>
   * DataSource 를 결정하지만 LazyConnectionDataSourceProxy 를 사용하여<br>
   * DataSource 결정을 실제 Statement 가 요청되었을 때로 설정
   * @author FreshR
   * @since 2023. 1. 12. 오후 5:52:51
   */
  @Primary
  @Bean(name = "dataSource")
  public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
  }

}
