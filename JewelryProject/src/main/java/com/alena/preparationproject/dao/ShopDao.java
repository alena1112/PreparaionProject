package com.alena.preparationproject.dao;

import com.alena.preparationproject.mvc.model.Shop;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class ShopDao extends Dao<Shop, Long> {
    @Override
    public Optional<Shop> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Shop> getAll() {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Shop> query = entityManager.createQuery("SELECT s FROM Shop s", Shop.class);
            return query.getResultList();
        });
    }

    @Override
    public void save(Shop shop) {

    }

    @Override
    public void update(Shop shop) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
