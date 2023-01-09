package run.freshr.common.config;

import static io.lettuce.core.models.role.RedisInstance.Role.MASTER;
import static io.lettuce.core.models.role.RedisInstance.Role.REPLICA;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
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

@Profile({"dev", "staging", "prod"})
@Slf4j
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class RoutingDataSourceConfig {

  @Bean(name = "dataSourceMain")
  @ConfigurationProperties(prefix="spring.datasource.main.hikari")
  public DataSource dataSourceMain() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean(name = "dataSourceReplica")
  @ConfigurationProperties(prefix="spring.datasource.replica.hikari")
  public DataSource dataSourceReplica() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

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

  @Primary
  @Bean(name = "dataSource")
  public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
  }

}
