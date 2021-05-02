package com.alena.jewelryproject.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageFileService {
    private static final Logger log = LoggerFactory.getLogger(ImageFileService.class);

    @Value("${images.path}") String imagesPath;

    public boolean uploadImage(String imageName, byte[] bytes) {
        FileOutputStream os = null;
        try {
            String path = imagesPath + "/" + imageName;
            File image = new File(path);
            if (!image.exists()) {
                image.createNewFile();
                os = new FileOutputStream(image);
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
                ImageIO.write(bufferedImage, imageName.substring(imageName.lastIndexOf(".") + 1), os);

                log.info(String.format("Image file %s was saved successfully", imageName));
                return true;
            } else {
                log.info(String.format("Image file %s already exists", imageName));
                return false;
            }
        } catch (Exception e) {
            log.error("Exception while saving new image", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("Exception while saving new image", e);
                }
            }
        }
        return false;
    }

    public byte[] loadImage(String imageName) {
        InputStream in = null;
        try {
            File file = loadFile(imageName);

            if (file != null) {
                in = new FileInputStream(file);
                return IOUtils.toByteArray(in);
            }
        } catch (Exception e) {
            log.error(String.format("Exception while loading image %s", imageName), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(String.format("Exception while loading image %s", imageName), e);
                }
            }
        }
        return null;
    }

    public boolean deleteImage(String imageName) {
        File file = loadFile(imageName);
        if (file != null) {
            return FileUtils.deleteQuietly(file);
        } else {
            log.info(String.format("Image %s not found", imageName));
            return true;
        }
    }

    private File loadFile(String name) {
        try {
            String path = imagesPath + "/" + name;
            File file = new File(path);

            if (file.exists()) {
                return file;
            }
        } catch (Exception e) {
            log.error(String.format("Exception while loading file %s", name), e);
        }
        return null;
    }
}
