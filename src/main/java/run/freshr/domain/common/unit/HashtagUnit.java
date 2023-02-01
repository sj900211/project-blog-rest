package run.freshr.domain.common.unit;

import java.util.List;
import org.springframework.data.domain.Page;
import run.freshr.common.extension.unit.UnitGetExtension;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.common.dto.response.HashtagStatisticsResponse;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.common.vo.CommonSearch;

public interface HashtagUnit extends UnitGetExtension<Hashtag, String> {

  List<Hashtag> getList();

  List<Hashtag> search(String keyword);

  List<HashtagStatisticsResponse> getStatistics();

  Page<Blog> getBlogPage(CommonSearch search);

  Page<Post> getPostPage(CommonSearch search);

}
