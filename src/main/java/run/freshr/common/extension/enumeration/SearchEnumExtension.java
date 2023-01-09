package run.freshr.common.extension.enumeration;

import com.querydsl.core.BooleanBuilder;
import run.freshr.mappers.EnumModel;

public interface SearchEnumExtension extends EnumModel {

  BooleanBuilder search(String word);

}
