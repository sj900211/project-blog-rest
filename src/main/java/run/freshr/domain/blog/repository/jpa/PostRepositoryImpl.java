package run.freshr.domain.blog.repository.jpa;

import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.domain.blog.entity.QPost.post;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import run.freshr.common.utils.QueryUtil;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.enumeration.PostSearchKeys;
import run.freshr.domain.blog.vo.BlogSearch;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Post> getPage(BlogSearch search) {
    LocalDateTime cursorAt = search.getCursorAt();

    JPAQuery<Post> query = queryFactory.selectFrom(post)
        .where(post.deleteFlag.isFalse(), post.useFlag.isTrue(),
            post.blog.id.eq(search.getBlogId()),
            post.blog.deleteFlag.isFalse(), post.blog.useFlag.isTrue());

    query.where(post.createAt.before(cursorAt));

    String word = search.getWord();

    if (hasLength(word)) {
      query.where(QueryUtil.searchEnum(word, PostSearchKeys.find(search.getKey())));
    }

    List<OrderSpecifier<?>> orderList = List.of(post.createAt.desc(), post.id.desc());

    return QueryUtil.paging(query, post, search, orderList);
  }

}
