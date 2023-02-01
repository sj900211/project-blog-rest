package run.freshr.domain.community.validator;

import static java.util.Objects.isNull;
import static run.freshr.common.utils.RestUtil.rejectRequired;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import run.freshr.domain.community.vo.CommunitySearch;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommunityValidator {

  public void getBoardPage(CommunitySearch search, Errors errors) {
    log.info("CommunityValidator.getBoardPage");

    Integer size = search.getSize();

    if (isNull(size)) {
      rejectRequired("size", errors);
    }
  }

}
