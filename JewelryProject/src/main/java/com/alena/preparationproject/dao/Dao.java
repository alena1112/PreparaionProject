package com.alena.preparationproject.dao;

import com.alena.preparationproject.web.model.IdentifiableEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Dao<T extends IdentifiableEntity, ID> {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public abstract Optional<T> get(ID id);

    public abstract List<T> getAll();

    public abstract void save(T t);

    public abstract void update(T t);

    public abstract void delete(ID id);

    private EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    protected void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    protected <R> R executeInsideTransaction(Function<EntityManager, R> action) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }
            R result = action.apply(entityManager);
            tx.commit();
            return result;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    protected boolean isDetached(T entity) {
        return entity.getId() != null;
    }
}
