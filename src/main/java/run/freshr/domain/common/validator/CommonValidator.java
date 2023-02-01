package run.freshr.domain.common.validator;

import static java.util.Objects.isNull;
import static run.freshr.common.utils.RestUtil.rejectRequired;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import run.freshr.domain.common.vo.CommonSearch;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonValidator {

  public void getBlogPageByHashtag(CommonSearch search, Errors errors) {
    log.info("CommonValidator.getBlogPageByHashtag");

    Integer size = search.getSize();

    if (isNull(size)) {
      rejectRequired("size", errors);
    }
  }

  public void getPostPageByHashtag(CommonSearch search, Errors errors) {
    log.info("CommonValidator.getPostPageByHashtag");

    Integer size = search.getSize();

    if (isNull(size)) {
      rejectRequired("size", errors);
    }
  }

}
