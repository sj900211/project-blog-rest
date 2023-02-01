package run.freshr.domain.community.repository.jpa;

import static com.querydsl.core.types.Order.DESC;
import static java.util.Locale.ROOT;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.domain.community.entity.QBoardNotice.boardNotice;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import run.freshr.common.utils.QueryUtil;
import run.freshr.domain.community.entity.BoardNotice;
import run.freshr.domain.community.enumeration.BoardNoticeOrderTypes;
import run.freshr.domain.community.enumeration.BoardNoticeSearchKeys;
import run.freshr.domain.community.vo.CommunitySearch;

@RequiredArgsConstructor
public class BoardNoticeRepositoryImpl implements BoardNoticeRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<BoardNotice> getPage(CommunitySearch search) {
    LocalDateTime cursorAt = search.getCursorAt();

    JPAQuery<BoardNotice> query = queryFactory.selectFrom(boardNotice)
        .where(boardNotice.deleteFlag.isFalse(), boardNotice.useFlag.isTrue());

    String word = search.getWord();

    if (hasLength(word)) {
      query.where(QueryUtil.searchEnum(word, BoardNoticeSearchKeys.find(search.getKey())));
    }

    String orderBy = search.getOrderBy();
    String orderType = search.getOrderType();
    List<OrderSpecifier<?>> orderList = new ArrayList<>();

    if (hasLength(orderType)) {
      BoardNoticeOrderTypes orderTypes = BoardNoticeOrderTypes.find(search.getKey());
      Order order = hasLength(orderBy) ? Order.valueOf(orderBy.toUpperCase(ROOT)) : DESC;

      switch (orderTypes) {
        case TITLE -> orderList.add(new OrderSpecifier<>(order, boardNotice.title));
        case FIXED -> orderList.add(new OrderSpecifier<>(order, boardNotice.fixed));
        case EXPOSE -> orderList.add(new OrderSpecifier<>(order, boardNotice.expose));
        case CREATOR -> orderList.add(new OrderSpecifier<>(order, boardNotice.creator.username));
      }
    } else {
      query.where(boardNotice.createAt.before(cursorAt));
      orderList.add(boardNotice.createAt.desc());
    }

    orderList.add(boardNotice.id.desc());

    return QueryUtil.paging(query, boardNotice, search, orderList);
  }

}
