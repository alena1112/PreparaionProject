package com.alena.preparationproject.admin.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class JewelryOrder {

    private List<JewelryItem> items;

    private double deliveryAmount;

    private String shopName;

    private Date date;

    public JewelryOrder() {
    }

    public JewelryOrder(List<JewelryItem> items, double deliveryAmount, String shopName, Date date) {
        this.items = items;
        this.deliveryAmount = deliveryAmount;
        this.shopName = shopName;
        this.date = date;
    }

    public List<JewelryItem> getItems() {
        return items;
    }

    @XmlElementWrapper(name = "jewelryItems")
    @XmlElement(name = "jewelryItem", type = JewelryItem.class)
    public void setItems(List<JewelryItem> items) {
        this.items = items;
    }

    @XmlElement
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @XmlElement
    public void setDate(Date date) {
        this.date = date;
    }

    public double getDeliveryAmount() {
        return deliveryAmount;
    }

    @XmlElement
    public void setDeliveryAmount(double deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public String getShopName() {
        return shopName;
    }

    public Date getDate() {
        return date;
    }
}
