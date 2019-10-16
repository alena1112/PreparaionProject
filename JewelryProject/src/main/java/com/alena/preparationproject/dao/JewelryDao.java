package com.alena.preparationproject.dao;

import com.alena.preparationproject.web.model.Jewelry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
public class JewelryDao implements Dao<Jewelry> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Jewelry> get(long id) {
        return Optional.ofNullable(entityManager.find(Jewelry.class, id));
    }

    @Override
    public List<Jewelry> getAll() {
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
    public void delete(Jewelry user) {
        executeInsideTransaction(entityManager -> entityManager.remove(user));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
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
