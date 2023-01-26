package run.freshr.domain.auth.redis;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import run.freshr.domain.auth.enumeration.Role;

@Slf4j
@RedisHash("MEM_ACCESS_TOKEN")
@Getter
public class AccessRedis {

  @Id
  private final String id;

  private final Long signedId;

  private final Role role;

  private AccessRedis(String id, Long signedId, Role role) {
    log.info("AccessRedis.Constructor");

    this.id = id;
    this.signedId = signedId;
    this.role = role;
  }

  public static AccessRedis createRedis(String id, Long signedId, Role role) {
    log.info("AccessRedis.createRedis");

    return new AccessRedis(id, signedId, role);
  }

}
