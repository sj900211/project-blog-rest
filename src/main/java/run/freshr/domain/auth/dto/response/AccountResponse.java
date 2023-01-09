package run.freshr.domain.auth.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.extension.response.ResponseExtension;
import run.freshr.domain.auth.enumeration.Privilege;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountResponse extends ResponseExtension<Long> {

  private Privilege privilege;

  private String username;

  private String name;

  private LocalDateTime signAt;

  private LocalDateTime removeAt;

  private Boolean useFlag;

}
