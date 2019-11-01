package com.alena.preparationproject.dao;

import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.model.enums.JewelryType;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
public class JewelryDao implements Dao<Jewelry, Long> {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<Jewelry> get(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return Optional.ofNullable(entityManager.find(Jewelry.class, id));
    }

    @Override
    public List<Jewelry> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Jewelry> query = entityManager.createQuery("SELECT j FROM Jewelry j", Jewelry.class);
        return query.getResultList();
    }

    public List<Jewelry> getAll(JewelryType type) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Jewelry> query = entityManager.createQuery("SELECT j FROM Jewelry j where j.type = :type", Jewelry.class);
        query.setParameter("type", type);
        return query.getResultList();
    }

    @Override
    public void save(Jewelry jewelry) {
        executeInsideTransaction(entityManager -> entityManager.persist(jewelry));
    }

    @Override
    public void update(Jewelry jewelry) {
        executeInsideTransaction(entityManager -> {
            entityManager.merge(jewelry);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(entityManager -> {
                    Jewelry foundJewelry = entityManager.find(Jewelry.class, id);
                    entityManager.remove(foundJewelry);
                }
        );
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
