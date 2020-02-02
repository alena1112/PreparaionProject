package com.alena.jewelryproject.dao;

import com.alena.jewelryproject.mvc.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDao extends Dao<Order, Long> {
    @Override
    public Optional<Order> get(Long id) {
        return executeInsideTransaction(entityManager ->
                Optional.ofNullable(entityManager.find(Order.class, id)));
    }

    @Override
    public List<Order> getAll() {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o", Order.class);
            return Optional.ofNullable(query.getResultList());
        }).orElse(new ArrayList<>());
    }

    @Override
    public void save(Order order) {
        executeInsideTransaction(entityManager -> {
            if (isDetached(order)) {
                entityManager.merge(order);
            } else {
                entityManager.persist(order);
            }
            return Optional.empty();
        });
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
