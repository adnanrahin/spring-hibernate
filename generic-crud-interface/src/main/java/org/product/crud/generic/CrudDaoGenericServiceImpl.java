package org.product.crud.generic;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CrudDaoGenericServiceImpl<T, ID> implements CrudDaoGenericService<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;
    private final Class<T> type;

    public CrudDaoGenericServiceImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("from " + type.getName(), type).getResultList();
    }

    @Override
    public T findById(ID id) {
        return entityManager.find(type, id);
    }

    @Override
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }
}
