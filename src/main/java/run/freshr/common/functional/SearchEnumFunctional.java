package run.freshr.common.functional;

import com.querydsl.core.BooleanBuilder;
import run.freshr.common.extension.enumeration.SearchEnumExtension;

@FunctionalInterface
public interface SearchEnumFunctional<E extends SearchEnumExtension> {

  BooleanBuilder search(String word, E enumeration);

}
