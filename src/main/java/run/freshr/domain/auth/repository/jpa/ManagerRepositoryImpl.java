package run.freshr.domain.auth.repository.jpa;

import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.common.utils.RestUtil.checkRole;
import static run.freshr.domain.auth.entity.QManager.manager;
import static run.freshr.domain.auth.entity.QStaff.staff;
import static run.freshr.domain.auth.enumeration.Privilege.BETA;
import static run.freshr.domain.auth.enumeration.Role.ROLE_BETA;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import run.freshr.common.utils.QueryUtil;
import run.freshr.domain.auth.entity.Manager;
import run.freshr.domain.auth.enumeration.AccountSearchKeys;
import run.freshr.domain.auth.vo.AuthSearch;

@RequiredArgsConstructor
public class ManagerRepositoryImpl implements ManagerRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Manager> getPage(AuthSearch search) {
    LocalDateTime cursorAt = search.getCursorAt();

    JPAQuery<Manager> query = queryFactory.selectFrom(manager)
        .where(manager.deleteFlag.isFalse(),
            manager.useFlag.isTrue(),
            manager.createAt.before(cursorAt));

    if (checkRole(ROLE_BETA)) {
      query.where(staff.privilege.eq(BETA));
    }

    String word = search.getWord();

    if (hasLength(word)) {
      query.where(QueryUtil.searchEnum(word, AccountSearchKeys.find(search.getKey())));
    }

    return QueryUtil.paging(query, manager, search);
  }

}
