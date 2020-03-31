package com.alena.jewelryproject.dao;

import com.alena.jewelryproject.model.PromotionalCode;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PromocodeDao extends Dao<PromotionalCode, Long> {

    @Override
    public Optional<PromotionalCode> get(Long id) {
        return executeInsideTransaction(entityManager -> Optional.ofNullable(entityManager.find(PromotionalCode.class, id)));
    }

    public PromotionalCode getByCode(String code) {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<PromotionalCode> query = entityManager.createQuery("SELECT c FROM PromotionalCode c " +
                    "where c.code = :code", PromotionalCode.class);
            query.setParameter("code", code);
            return query.getResultStream()
                    .findFirst();
        }).orElse(null);
    }

    @Override
    public List<PromotionalCode> getAll() {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<PromotionalCode> query = entityManager.createQuery("SELECT c FROM PromotionalCode c", PromotionalCode.class);
            return Optional.ofNullable(query.getResultList());
        }).orElse(new ArrayList<>());
    }

    @Override
    public void save(PromotionalCode promotionalCode) {
        executeInsideTransaction(entityManager -> {
            entityManager.persist(promotionalCode);
            return Optional.empty();
        });
    }

    @Override
    public void update(PromotionalCode promotionalCode) {
        executeInsideTransaction(entityManager -> Optional.ofNullable(entityManager.merge(promotionalCode)));
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(entityManager -> {
                    PromotionalCode promotionalCode = entityManager.find(PromotionalCode.class, id);
                    entityManager.remove(promotionalCode);
                    return Optional.empty();
                }
        );
    }
}
