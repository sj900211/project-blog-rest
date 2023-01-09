package run.freshr.domain.auth.repository.redis;

import org.springframework.data.repository.CrudRepository;
import run.freshr.domain.auth.redis.RsaPair;

public interface RsaPairRepository extends CrudRepository<RsaPair, String> {

}
