package run.freshr.domain.common.repository.jpa;

import java.util.List;
import org.springframework.data.domain.Page;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.common.dto.response.HashtagStatisticsResponse;
import run.freshr.domain.common.vo.CommonSearch;

public interface HashtagRepositoryCustom {

  List<HashtagStatisticsResponse> getStatistics();

  Page<Blog> getBlogPage(CommonSearch search);

  Page<Post> getPostPage(CommonSearch search);

}
