package com.alena.jewelryproject.model;

import com.alena.jewelryproject.FormatHelper;
import com.alena.jewelryproject.model.enums.Shop;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "material_order")
public class MaterialOrder extends IdentifiableEntity {
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Material> materials;

    @Column(name = "delivery_price")
    private double deliveryPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "shop")
    private Shop shop;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getFormatOrder() {
        return String.format("%s, дата %s, доставка %s", shop.name(), FormatHelper.formatDate.format(purchaseDate), deliveryPrice);
    }
}
