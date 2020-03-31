package com.alena.jewelryproject.model;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "material")
public class Material extends IdentifiableEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "unit_price_with_delivery")
    private Double unitPriceWithDelivery;

    @Column(name = "number")
    private Integer number;

    @Column(name = "image_url")
    private String imageURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_order_id")
    private MaterialOrder order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getUnitPriceWithDelivery() {
        return unitPriceWithDelivery;
    }

    public void setUnitPriceWithDelivery(Double unitPriceWithDelivery) {
        this.unitPriceWithDelivery = unitPriceWithDelivery;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public MaterialOrder getOrder() {
        return order;
    }

    public void setOrder(MaterialOrder order) {
        this.order = order;
    }
}
