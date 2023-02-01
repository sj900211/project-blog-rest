package run.freshr.domain.mapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.auth.dto.response.AccountResponse;
import run.freshr.domain.blog.enumeration.BlogRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPermissionMappingForBlogResponse {

  private AccountResponse account;

  private BlogRole role;

}
