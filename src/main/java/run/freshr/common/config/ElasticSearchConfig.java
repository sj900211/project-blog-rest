package run.freshr.common.config;

import static java.util.Objects.isNull;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

  @Value("${freshr.elasticsearch.host}")
  private String host;

  @Value("${ELASTICSEARCH_9200_PORT}")
  private Integer port;

  @Value("${ELASTICSEARCH_USERNAME}")
  private String username;

  @Value("${ELASTICSEARCH_PASSWORD}")
  private String password;

  @Override
  public RestHighLevelClient elasticsearchClient() {
    String url = host + (!isNull(port) ? (":" + port) : "");

    return RestClients.create(ClientConfiguration
            .builder()
            .connectedTo(url)
            .withBasicAuth(username, password)
            .build())
        .rest();
  }

}
