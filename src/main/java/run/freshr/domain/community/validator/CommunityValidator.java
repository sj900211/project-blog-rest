package run.freshr.domain.community.validator;

import static java.util.Objects.isNull;
import static run.freshr.common.utils.RestUtil.rejectRequired;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import run.freshr.domain.community.vo.CommunitySearch;

@Component
@RequiredArgsConstructor
public class CommunityValidator {

  public void getBoardPage(CommunitySearch search, Errors errors) {
    Integer size = search.getSize();

    if (isNull(size)) {
      rejectRequired("size", errors);
    }
  }

}
