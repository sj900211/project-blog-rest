package run.freshr.domain.blog.repository.jpa;

import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.domain.blog.entity.QBlog.blog;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import run.freshr.common.utils.QueryUtil;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.enumeration.BlogSearchKeys;
import run.freshr.domain.blog.vo.BlogSearch;

@RequiredArgsConstructor
public class BlogRepositoryImpl implements BlogRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Blog> getPage(BlogSearch search) {
    LocalDateTime cursorAt = search.getCursorAt();

    JPAQuery<Blog> query = queryFactory.selectFrom(blog)
        .where(blog.deleteFlag.isFalse(), blog.useFlag.isTrue());

    query.where(blog.createAt.before(cursorAt));

    String word = search.getWord();

    if (hasLength(word)) {
      query.where(QueryUtil.searchEnum(word, BlogSearchKeys.find(search.getKey())));
    }

    List<OrderSpecifier<?>> orderList = List.of(blog.createAt.desc(), blog.id.desc());

    return QueryUtil.paging(query, blog, search, orderList);
  }

}
