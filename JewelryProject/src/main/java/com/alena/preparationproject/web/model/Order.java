package com.alena.preparationproject.web.model;

import com.alena.preparationproject.web.model.enums.DeliveryType;
import com.alena.preparationproject.web.model.enums.PaymentType;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order extends IdentifiableEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_jewelry",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "jewelry_id")})
    private List<Jewelry> jewelries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promocode_id")
    private PromotionalCode promocode;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Embedded
    private UserData userData;

    public List<Jewelry> getJewelries() {
        return jewelries;
    }

    public void setJewelries(List<Jewelry> jewelries) {
        this.jewelries = jewelries;
    }

    public PromotionalCode getPromocode() {
        return promocode;
    }

    public void setPromocode(PromotionalCode promocode) {
        this.promocode = promocode;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
