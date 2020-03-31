package com.alena.jewelryproject.dao;

import com.alena.jewelryproject.model.Settings;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class SettingsDao extends Dao<Settings, Long> {

    @Override
    public Optional<Settings> get(Long id) {
        return executeInsideTransaction(entityManager -> {
            TypedQuery<Settings> query = entityManager.createQuery("SELECT s FROM Settings s " +
                    "where s.id = :id", Settings.class);
            query.setParameter("id", id);
            return query.getResultStream()
                    .findFirst();
        });
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
            return Optional.ofNullable(query.getResultList());
        }).orElse(null);
    }

    @Override
    public void save(Settings settings) {
        executeInsideTransaction(entityManager -> {
            entityManager.persist(settings);
            return Optional.empty();
        });
    }

    @Override
    public void update(Settings settings) {
        executeInsideTransaction(entityManager -> Optional.ofNullable(entityManager.merge(settings)));
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(entityManager -> {
                    Settings settings = entityManager.find(Settings.class, id);
                    entityManager.remove(settings);
                    return Optional.empty();
                }
        );
    }
}
