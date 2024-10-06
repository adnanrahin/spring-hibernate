package org.product.crud.generic;

import java.util.List;

public interface CrudDaoGenericService<T, ID> {

    List<T> findAll();

    T findById(ID id);

    T save(T entity);

    void delete(T entity);

    void deleteById(ID id);

}
