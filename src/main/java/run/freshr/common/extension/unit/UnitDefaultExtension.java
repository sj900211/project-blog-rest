package run.freshr.common.extension.unit;

public interface UnitDefaultExtension<E, ID> {

  ID create(E entity);

  Boolean exists(ID id);

  E get(ID id);

}
