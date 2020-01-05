package com.alena.preparationproject.dao;

import com.alena.preparationproject.mvc.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDao extends Dao<Order, Long> {
    @Override
    public Optional<Order> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o", Order.class);
            return query.getResultList();
        });
    }

    @Override
    public void save(Order order) {
        executeInsideTransaction(entityManager -> {
            if (isDetached(order)) {
                entityManager.merge(order);
            } else {
                entityManager.persist(order);
            }
        });
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
