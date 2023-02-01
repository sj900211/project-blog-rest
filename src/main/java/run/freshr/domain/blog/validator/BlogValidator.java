package run.freshr.domain.blog.validator;

import static java.util.Objects.isNull;
import static run.freshr.common.utils.RestUtil.rejectRequired;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import run.freshr.domain.blog.vo.BlogSearch;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlogValidator {

  public void getBlogPage(BlogSearch search, Errors errors) {
    log.info("BlogValidator.getBlogPage");

    Integer size = search.getSize();

    if (isNull(size)) {
      rejectRequired("size", errors);
    }
  }

  public void getPostPage(BlogSearch search, Errors errors) {
    log.info("BlogValidator.getPostPage");

    Integer size = search.getSize();

    if (isNull(size)) {
      rejectRequired("size", errors);
    }
  }

}
