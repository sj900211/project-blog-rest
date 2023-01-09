package run.freshr.domain.auth.redis;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Slf4j
@RedisHash("MEM_RSA_PAIR")
@Getter
public class RsaPair {

  @Id
  private final String publicKey;

  private final String privateKey;

  private final LocalDateTime createAt;

  private RsaPair(String publicKey, String privateKey, LocalDateTime createAt) {
    log.info("RsaPair.Constructor");

    this.publicKey = publicKey;
    this.privateKey = privateKey;
    this.createAt = createAt;
  }

  public static RsaPair createRedis(String publicKey, String privateKey,
      LocalDateTime createAt) {
    log.info("RsaPair.createRedis");

    return new RsaPair(publicKey, privateKey, createAt);
  }

}
