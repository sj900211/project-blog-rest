package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.domain.Page;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.vo.BlogSearch;

public interface BlogRepositoryCustom {

  Page<Blog> getPage(BlogSearch search);

}
