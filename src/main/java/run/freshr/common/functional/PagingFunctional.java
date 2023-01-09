package run.freshr.common.functional;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import run.freshr.common.extension.request.SearchExtension;

@FunctionalInterface
public interface PagingFunctional<E, S extends SearchExtension<?>> {

  Page<E> paging(JPAQuery<E> query, S search);

}
