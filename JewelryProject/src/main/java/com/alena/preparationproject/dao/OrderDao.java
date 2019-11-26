package com.alena.preparationproject.dao;

import com.alena.preparationproject.web.model.Order;
import org.springframework.stereotype.Repository;

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
        return null;
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
