package run.freshr.domain.auth.repository.jpa;

import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.common.utils.RestUtil.checkRole;
import static run.freshr.domain.auth.entity.QStaff.staff;
import static run.freshr.domain.auth.enumeration.Privilege.DELTA;
import static run.freshr.domain.auth.enumeration.Role.ROLE_DELTA;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import run.freshr.common.utils.QueryUtil;
import run.freshr.domain.auth.entity.Staff;
import run.freshr.domain.auth.enumeration.AccountSearchKeys;
import run.freshr.domain.auth.vo.AuthSearch;

@RequiredArgsConstructor
public class StaffRepositoryImpl implements StaffRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Staff> getPage(AuthSearch search) {
    LocalDateTime cursorAt = search.getCursorAt();

    JPAQuery<Staff> query = queryFactory.selectFrom(staff)
        .where(staff.deleteFlag.isFalse(),
            staff.useFlag.isTrue(),
            staff.createAt.before(cursorAt));

    if (checkRole(ROLE_DELTA)) {
      query.where(staff.privilege.eq(DELTA));
    }

    String word = search.getWord();

    if (hasLength(word)) {
      query.where(QueryUtil.searchEnum(word, AccountSearchKeys.find(search.getKey())));
    }

    return QueryUtil.paging(query, staff, search);
  }

}
