package com.alena.jewelryproject.service;

import com.alena.jewelryproject.model.Jewelry;

public class CreateOrderException extends Exception {
    private ExceptionType exceptionType;
    private Jewelry jewelry;

    public CreateOrderException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public CreateOrderException(ExceptionType exceptionType, Jewelry jewelry) {
        this.exceptionType = exceptionType;
        this.jewelry = jewelry;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public Jewelry getJewelry() {
        return jewelry;
    }

    public void setJewelry(Jewelry jewelry) {
        this.jewelry = jewelry;
    }

    public enum ExceptionType {
        JEWELRY_SOLD,
        JEWELRY_PRICE_CHANGE,
        JEWELRY_DOES_NOT_EXIST,
        PROMOCODE_DOES_NOT_EXIST,
        PROMOCODE_IS_NOT_VALID,
        PROMOCODE_IS_CHANGED,
        DELIVERY_IS_CHANGED
    }
}
