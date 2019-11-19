package com.alena.preparationproject.web.service;

import com.alena.preparationproject.web.model.Jewelry;

public class CreateOrderException extends Exception {
    private Jewelry soldJewelry;
    private ExceptionType type;

    public CreateOrderException(Jewelry soldJewelry) {
        this.soldJewelry = soldJewelry;
        this.type = ExceptionType.SOLD;
    }

    public enum ExceptionType {SOLD}
}
