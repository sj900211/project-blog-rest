package run.freshr.domain.mapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.dto.response.IdResponse;
import run.freshr.domain.auth.dto.response.AccountResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequestMappingForBlogResponse {

  private AccountResponse account;

}
