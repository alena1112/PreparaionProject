package com.alena.preparationproject.dao;

import com.alena.preparationproject.mvc.model.Image;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ImageDao extends Dao<Image, Long> {
    @Override
    public Optional<Image> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Image> getAll() {
        return null;
    }

    @Override
    public void save(Image image) {
        executeInsideTransaction(entityManager -> {
            entityManager.persist(image);
        });
    }

    @Override
    public void update(Image image) {

    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(entityManager -> {
                    Image foundImage = entityManager.find(Image.class, id);
                    entityManager.remove(foundImage);
                }
        );
    }
}
