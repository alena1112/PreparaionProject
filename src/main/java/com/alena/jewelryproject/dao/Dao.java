package com.alena.jewelryproject.dao;

import com.alena.jewelryproject.mvc.model.IdentifiableEntity;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Dao<T extends IdentifiableEntity, ID> {
    private static final Logger log = LoggerFactory.getLogger(Dao.class);

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

    protected <R> Optional<R> executeInsideTransaction(Function<EntityManager, Optional<R>> action) {
        int attempt = 1;
        while (attempt != 4) {
            try {
                Optional<R> result = execute(action);
                return result;
            } catch (JDBCConnectionException e) {
                log.error(String.format("Error while executing transaction. Attempt %s", attempt));
                attempt++;
            }
        }
        return Optional.empty();
    }

    private <R> Optional<R> execute(Function<EntityManager, Optional<R>> action) throws JDBCConnectionException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }
            Optional<R> result = action.apply(entityManager);
            tx.commit();
            return result;
        } catch (Exception e) {
            log.error("Exception while transaction is commit", e);
            try {
                tx.rollback();
            } catch (Exception ex) {
                log.error("Exception while transaction is rollback", ex);
            }
            return Optional.empty();
        }
    }

    protected boolean isDetached(T entity) {
        return entity.getId() != null;
    }
}
