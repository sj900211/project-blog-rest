package run.freshr.domain.common.repository.jpa;

import static run.freshr.domain.blog.entity.QBlog.blog;
import static run.freshr.domain.blog.entity.QPost.post;
import static run.freshr.domain.common.entity.QHashtag.hashtag;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import run.freshr.common.utils.QueryUtil;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.common.dto.response.HashtagStatisticsResponse;
import run.freshr.domain.common.dto.response.QHashtagStatisticsResponse;
import run.freshr.domain.common.vo.CommonSearch;

@RequiredArgsConstructor
public class HashtagRepositoryImpl implements HashtagRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<HashtagStatisticsResponse> getStatistics() {
    return queryFactory
        .select(new QHashtagStatisticsResponse(
            hashtag.id,
            JPAExpressions
                .select(blog.count())
                .from(blog)
                .where(blog.deleteFlag.isFalse(), blog.useFlag.isTrue()),
            JPAExpressions
                .select(post.count())
                .from(post)
                .where(post.blog.deleteFlag.isFalse(), post.blog.useFlag.isTrue())
        ))
        .from(hashtag)
        .orderBy(hashtag.id.asc())
        .fetch();
  }

  @Override
  public Page<Blog> getBlogPage(CommonSearch search) {
    LocalDateTime cursorAt = search.getCursorAt();

    JPAQuery<Blog> query = queryFactory.selectFrom(blog)
        .where(blog.deleteFlag.isFalse(), blog.useFlag.isTrue(),
            blog.hashtagList.any().hashtag.id.in(search.getHashtags()));

    query.where(blog.createAt.before(cursorAt));

    List<OrderSpecifier<?>> orderList = List.of(blog.createAt.desc(), blog.id.desc());

    return QueryUtil.paging(query, blog, search, orderList);
  }

  @Override
  public Page<Post> getPostPage(CommonSearch search) {
    LocalDateTime cursorAt = search.getCursorAt();

    JPAQuery<Post> query = queryFactory.selectFrom(post)
        .where(post.deleteFlag.isFalse(), post.useFlag.isTrue(),
            post.hashtagList.any().hashtag.id.in(search.getHashtags()));

    query.where(post.createAt.before(cursorAt));

    List<OrderSpecifier<?>> orderList = List.of(post.createAt.desc(), post.id.desc());

    return QueryUtil.paging(query, post, search, orderList);
  }

}
