package com.alena.preparationproject.dao;

import com.alena.preparationproject.mvc.model.Jewelry;
import com.alena.preparationproject.mvc.model.enums.JewelryType;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class JewelryDao extends Dao<Jewelry, Long> {
    @Override
    public Optional<Jewelry> get(Long id) {
        return Optional.ofNullable(executeInsideTransaction(entityManager -> {
            return entityManager.find(Jewelry.class, id);
        }));
    }

    @Override
    public List<Jewelry> getAll() {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Jewelry> query = entityManager.createQuery("SELECT j FROM Jewelry j", Jewelry.class);
            return query.getResultList();
        });
    }

    public List<Jewelry> getAllUnhidden() {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Jewelry> query = entityManager.createQuery("SELECT j FROM Jewelry j where j.isHide = false " +
                    "order by j.isSold", Jewelry.class);
            return query.getResultList();
        });
    }

    public List<Jewelry> getAllUnhidden(JewelryType type) {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Jewelry> query = entityManager.createQuery("SELECT j FROM Jewelry j where j.type = :type and " +
                    "j.isHide = false order by j.isSold", Jewelry.class);
            query.setParameter("type", type);
            return query.getResultList();
        });
    }

    @Override
    public void save(Jewelry jewelry) {
        executeInsideTransaction(entityManager -> {
            entityManager.persist(jewelry);
        });
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
}
