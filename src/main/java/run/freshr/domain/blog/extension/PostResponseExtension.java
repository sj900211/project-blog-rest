package run.freshr.domain.blog.extension;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.extension.response.ResponseAuditLogicalExtension;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostResponseExtension extends ResponseAuditLogicalExtension<Long> {

  protected String title;

}
