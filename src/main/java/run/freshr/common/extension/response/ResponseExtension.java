package run.freshr.common.extension.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseExtension<ID> {

  protected ID id;

  protected LocalDateTime createAt;

  protected LocalDateTime updateAt;

}
