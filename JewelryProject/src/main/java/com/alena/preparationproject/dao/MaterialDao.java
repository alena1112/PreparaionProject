package com.alena.preparationproject.dao;

import com.alena.preparationproject.mvc.model.Material;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class MaterialDao extends Dao<Material, Long> {
    @Override
    public Optional<Material> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Material> getAll() {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Material> query = entityManager.createQuery("SELECT m FROM Material m", Material.class);
            return query.getResultList();
        });
    }

    @Override
    public void save(Material material) {

    }

    @Override
    public void update(Material material) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
