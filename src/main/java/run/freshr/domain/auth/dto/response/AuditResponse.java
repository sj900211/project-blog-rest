package run.freshr.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.auth.enumeration.Privilege;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditResponse {

  private Long id;

  private Privilege privilege;

  private String username;

  private String name;

}
