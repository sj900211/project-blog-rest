package run.freshr.domain.auth.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import run.freshr.annotation.SearchClass;
import run.freshr.common.extension.request.SearchExtension;

@Data
@SearchClass
@EqualsAndHashCode(callSuper = true)
public class AuthSearch extends SearchExtension<Long> {

}
