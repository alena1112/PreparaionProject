package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.ImageRepository;
import com.alena.jewelryproject.model.Image;
import com.alena.jewelryproject.model.Jewelry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageFileService imageFileService;

    public Image uploadImage(String imageName, byte[] bytes, int index, Jewelry jewelry) {
        log.info("Image " + imageName + " start to load");
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
        imageRepository.save(image);
    }

    public void delete(Image image) {
        if (image != null) {
            imageFileService.deleteImage(image.getName());
        }
    }
}
