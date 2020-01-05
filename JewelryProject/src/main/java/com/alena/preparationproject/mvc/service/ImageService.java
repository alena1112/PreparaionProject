package com.alena.preparationproject.mvc.service;

import com.alena.preparationproject.dao.ImageDao;
import com.alena.preparationproject.mvc.model.Image;
import com.alena.preparationproject.mvc.model.Jewelry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private ImageFileService imageFileService;

    public Image uploadImage(String imageName, byte[] bytes, int index, Jewelry jewelry) {
        if (imageFileService.uploadImage(imageName, bytes)) {
            Image imageEntity = new Image();
            imageEntity.setJewelry(jewelry);
            imageEntity.setName(imageName);
            imageEntity.setIndex(index);
            return imageEntity;
        } else {
            return null;
        }
    }

    public void save(Image image) {
        imageDao.update(image);
    }

    public void delete(Image image) {
        if (image != null) {
            if (image.getId() != null) {
                imageDao.delete(image.getId());
            }
            imageFileService.deleteImage(image.getName());
        }
    }
}
