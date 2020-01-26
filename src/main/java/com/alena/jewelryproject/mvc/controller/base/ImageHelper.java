package com.alena.jewelryproject.mvc.controller.base;

import com.alena.jewelryproject.mvc.model.Image;
import com.alena.jewelryproject.mvc.model.Jewelry;

public class ImageHelper {
    private Jewelry jewelry;
    private String contextPath;

    public ImageHelper(String contextPath) {
        this.contextPath = contextPath;
    }

    public ImageHelper(Jewelry jewelry, String contextPath) {
        this.jewelry = jewelry;
        this.contextPath = contextPath;
    }

    public static String getImageFullPath(String imageName, String contextPath) {
        return contextPath + "/image?name=" + imageName;
    }

    public String getMainImageFullPath(Jewelry jewelry) {
        Image mainImage = getMainImage(jewelry);
        return mainImage != null ? getImageFullPath(mainImage.getName(), contextPath) : "";
    }

    public String getMainImageFullPath() {
        Image mainImage = getMainImage(jewelry);
        return mainImage != null ? getImageFullPath(mainImage.getName(), contextPath) : "";
    }

    public String getMainImageFullPathOrDefault() {
        String path = getMainImageFullPath();
        return path.equals("") ? getDefaultImage() : path;
    }

    public String getImageFullPath(int index) {
        Image image = jewelry.getImage(index);
        return image != null ? getImageFullPath(image.getName(), contextPath) : "";
    }

    public String getImageFullPathOrDefault(int index) {
        String path = getImageFullPath(index);
        return path.equals("") ? getDefaultImage() : path;
    }

    public String getDefaultImage() {
        return contextPath + "/resources/images/icons-plus.png";
    }

    private Image getMainImage(Jewelry jewelry) {
        return jewelry.getImage(0);
    }
}
