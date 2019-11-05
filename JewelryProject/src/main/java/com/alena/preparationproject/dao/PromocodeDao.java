package com.alena.preparationproject.dao;

import com.alena.preparationproject.web.model.PromotionalCode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class PromocodeDao implements Dao<PromotionalCode, Long> {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<PromotionalCode> get(Long aLong) {
        return Optional.empty();
    }

    public PromotionalCode getByCode(String code) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<PromotionalCode> query = entityManager.createQuery("SELECT c FROM PromotionalCode c " +
                        "where c.code = :code", PromotionalCode.class);
        query.setParameter("code", code);
        return query.getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<PromotionalCode> getAll() {
        return null;
    }

    @Override
    public void save(PromotionalCode promotionalCode) {

    }

    @Override
    public void update(PromotionalCode promotionalCode) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
