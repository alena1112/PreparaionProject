package com.alena.preparationproject.web.controller;

public class ResponseMessage {
    private Boolean isValidPromocode;
    private Double promocode;
    private Double totalPrice;
    private Double deliveryPrice;

    public Boolean getValidPromocode() {
        return isValidPromocode;
    }

    public void setValidPromocode(Boolean validPromocode) {
        isValidPromocode = validPromocode;
    }

    public Double getPromocode() {
        return promocode;
    }

    public void setPromocode(Double promocode) {
        this.promocode = promocode;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
}
