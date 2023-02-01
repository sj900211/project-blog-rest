package run.freshr.domain.auth.repository.jpa;

import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.domain.auth.entity.QAccount.account;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import run.freshr.common.utils.QueryUtil;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.enumeration.AccountSearchKeys;
import run.freshr.domain.auth.vo.AuthSearch;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Account> getPage(AuthSearch search) {
    LocalDateTime cursorAt = search.getCursorAt();

    JPAQuery<Account> query = queryFactory.selectFrom(account)
        .where(account.deleteFlag.isFalse(),
            account.useFlag.isTrue(),
            account.createAt.before(cursorAt));

    String word = search.getWord();

    if (hasLength(word)) {
      query.where(QueryUtil.searchEnum(word, AccountSearchKeys.find(search.getKey())));
    }

    List<OrderSpecifier<?>> orderList = List.of(account.createAt.desc(), account.id.desc());

    return QueryUtil.paging(query, account, search, orderList);
  }

}
