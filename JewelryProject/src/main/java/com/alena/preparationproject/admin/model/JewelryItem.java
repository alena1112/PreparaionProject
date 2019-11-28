package com.alena.preparationproject.admin.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JewelryItem {

    private JewelryName jewelryName;

    private String url;

    private String imageURL;

    private Integer number;

    private Double price;

    private Double priceWithDelivery;

    public JewelryItem() {
    }

    public JewelryItem(JewelryName jewelryName, String imageURL, Integer number, Double price) {
        this.jewelryName = jewelryName;
        this.imageURL = imageURL;
        this.number = number;
        this.price = price;
    }

    public JewelryItem(JewelryName jewelryName, Integer number, Double price) {
        this.jewelryName = jewelryName;
        this.number = number;
        this.price = price;
    }

    public JewelryName getJewelryName() {
        return jewelryName;
    }

    @XmlElement(type = JewelryName.class)
    public void setJewelryName(JewelryName jewelryName) {
        this.jewelryName = jewelryName;
    }

    public String getImageURL() {
        return imageURL;
    }

    @XmlElement
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Integer getNumber() {
        return number;
    }

    @XmlElement
    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getPrice() {
        return price;
    }

    @XmlElement
    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceWithDelivery() {
        return priceWithDelivery;
    }

    @XmlElement
    public void setPriceWithDelivery(Double priceWithDelivery) {
        this.priceWithDelivery = priceWithDelivery;
    }

    public String getUrl() {
        return url;
    }

    @XmlElement
    public void setUrl(String url) {
        this.url = url;
    }
}
