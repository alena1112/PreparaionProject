package com.alena.jewelryproject.dao;

import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.enums.JewelryType;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JewelryDao extends Dao<Jewelry, Long> {
    @Override
    public Optional<Jewelry> get(Long id) {
        return executeInsideTransaction(entityManager ->
                Optional.ofNullable(entityManager.find(Jewelry.class, id)));
    }

    @Override
    public List<Jewelry> getAll() {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Jewelry> query = entityManager.createQuery("SELECT j FROM Jewelry j order by j.createdDate desc", Jewelry.class);
            return Optional.ofNullable(query.getResultList());
        }).orElse(new ArrayList<>());
    }

    public List<Jewelry> getAllUnhidden() {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Jewelry> query = entityManager.createQuery("SELECT j FROM Jewelry j where j.isHide = false " +
                    "order by j.isSold", Jewelry.class);
            return Optional.ofNullable(query.getResultList());
        }).orElse(new ArrayList<>());
    }

    public List<Jewelry> getAllUnhidden(int count) {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Jewelry> query = entityManager.createQuery("SELECT j FROM Jewelry j where j.isHide = false " +
                    "order by j.isSold, j.createdDate", Jewelry.class);
            query.setMaxResults(count);
            return Optional.ofNullable(query.getResultList());
        }).orElse(new ArrayList<>());
    }

    public List<Jewelry> getAllUnhidden(JewelryType type) {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Jewelry> query = entityManager.createQuery("SELECT j FROM Jewelry j where j.type = :type and " +
                    "j.isHide = false order by j.isSold", Jewelry.class);
            query.setParameter("type", type);
            return Optional.ofNullable(query.getResultList());
        }).orElse(new ArrayList<>());
    }

    @Override
    public void save(Jewelry jewelry) {
        executeInsideTransaction(entityManager -> {
            entityManager.persist(jewelry);
            return Optional.empty();
        });
    }

    @Override
    public void update(Jewelry jewelry) {
        executeInsideTransaction(entityManager -> Optional.ofNullable(entityManager.merge(jewelry)));
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(entityManager -> {
                    Jewelry foundJewelry = entityManager.find(Jewelry.class, id);
                    entityManager.remove(foundJewelry);
                    return Optional.empty();
                }
        );
    }
}
