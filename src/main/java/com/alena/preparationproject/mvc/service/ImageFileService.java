package com.alena.preparationproject.mvc.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageFileService {
    private static final Logger log = LoggerFactory.getLogger(ImageFileService.class);
    private final int IMAGE_QUALITY = 3;

    @Autowired
    private ServletContext servletContext;

    public boolean uploadImage(String imageName, byte[] bytes) {
        FileOutputStream os = null;
        try {
            String imagesPath = getImagePath();
            String path = imagesPath + "/" + imageName;
            File image = new File(path);
            if (!image.exists()) {
                image.createNewFile();
                os = new FileOutputStream(image);
                changeImageSize(new ByteArrayInputStream(bytes), os, imageName);

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
                log.info(String.format("Image %s loaded successfully", imageName));
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

    public String getImagePath() {
        return servletContext.getInitParameter("com.alena.preparationproject.images");
    }

    private File loadFile(String name) {
        try {
            String path = getImagePath() + "/" + name;
            File file = new File(path);

            if (file.exists()) {
                return file;
            }
        } catch (Exception e) {
            log.error(String.format("Exception while loading file %s", name), e);
        }
        return null;
    }

    private void changeImageSize(InputStream is, FileOutputStream fos, String imageName) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(is);
        if (bufferedImage.getWidth() > 1500 && bufferedImage.getHeight() > 1500) {
            bufferedImage = Scalr.resize(bufferedImage, Scalr.Method.QUALITY,
                    bufferedImage.getWidth() / IMAGE_QUALITY,
                    bufferedImage.getHeight() / IMAGE_QUALITY);
        }
        ImageIO.write(bufferedImage, imageName.substring(imageName.lastIndexOf(".") + 1), fos);
    }
}
