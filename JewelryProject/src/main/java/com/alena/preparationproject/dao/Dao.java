package com.alena.preparationproject.dao;

import com.alena.preparationproject.web.model.IdentifiableEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Dao<T extends IdentifiableEntity, ID> {
    Optional<T> get(ID id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(ID id);
}
