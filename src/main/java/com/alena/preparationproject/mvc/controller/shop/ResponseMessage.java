package com.alena.preparationproject.mvc.controller.shop;

public class ResponseMessage {
    private Boolean isValidPromocode;
    private String promocodeLimit;
    private String formatPromocode;
    private String formatTotalCost;
    private String formatCostWithoutDiscount;
    private String formatDeliveryPrice;

    public Boolean getValidPromocode() {
        return isValidPromocode;
    }

    public void setValidPromocode(Boolean validPromocode) {
        isValidPromocode = validPromocode;
    }

    public String getPromocodeLimit() {
        return promocodeLimit;
    }

    public void setPromocodeLimit(String promocodeLimit) {
        this.promocodeLimit = promocodeLimit;
    }

    public String getFormatPromocode() {
        return formatPromocode;
    }

    public void setFormatPromocode(String formatPromocode) {
        this.formatPromocode = formatPromocode;
    }

    public String getFormatTotalCost() {
        return formatTotalCost;
    }

    public void setFormatTotalCost(String formatTotalCost) {
        this.formatTotalCost = formatTotalCost;
    }

    public String getFormatDeliveryPrice() {
        return formatDeliveryPrice;
    }

    public void setFormatDeliveryPrice(String formatDeliveryPrice) {
        this.formatDeliveryPrice = formatDeliveryPrice;
    }

    public String getFormatCostWithoutDiscount() {
        return formatCostWithoutDiscount;
    }

    public void setFormatCostWithoutDiscount(String formatCostWithoutDiscount) {
        this.formatCostWithoutDiscount = formatCostWithoutDiscount;
    }
}
