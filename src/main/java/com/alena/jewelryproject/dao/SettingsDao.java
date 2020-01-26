package com.alena.jewelryproject.dao;

import com.alena.jewelryproject.mvc.model.Settings;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class SettingsDao extends Dao<Settings, Long> {
    @Override
    public Optional<Settings> get(Long aLong) {
        return Optional.empty();
    }

    public Optional<Settings> get(String key) {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Settings> query = entityManager.createQuery("SELECT s FROM Settings s " +
                    "where s.key = :key", Settings.class);
            query.setParameter("key", key);
            return query.getResultStream()
                    .findFirst();
        });
    }

    @Override
    public List<Settings> getAll() {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Settings> query = entityManager.createQuery("SELECT s FROM Settings s", Settings.class);
            return query.getResultList();
        });
    }

    @Override
    public void save(Settings settings) {

    }

    @Override
    public void update(Settings settings) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
