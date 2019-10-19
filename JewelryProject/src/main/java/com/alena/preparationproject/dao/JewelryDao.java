package com.alena.preparationproject.dao;

import com.alena.preparationproject.web.model.Jewelry;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
public class JewelryDao implements Dao<Jewelry> {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<Jewelry> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return Optional.ofNullable(entityManager.find(Jewelry.class, id));
    }

    @Override
    public List<Jewelry> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Jewelry> query = entityManager.createQuery("SELECT j FROM Jewelry j", Jewelry.class);
        return query.getResultList();
    }

    @Override
    public void save(Jewelry jewelry) {
        executeInsideTransaction(entityManager -> entityManager.persist(jewelry));
    }

    @Override
    public void update(Jewelry jewelry, Map<String, Object> params) {
        params.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Jewelry.class, key);
            if (field != null) {
                ReflectionUtils.setField(field, jewelry, value);
            }
        });
        executeInsideTransaction(entityManager -> entityManager.merge(jewelry));
    }

    @Override
    public void delete(Jewelry jewelry) {
        executeInsideTransaction(entityManager -> entityManager.remove(jewelry));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
