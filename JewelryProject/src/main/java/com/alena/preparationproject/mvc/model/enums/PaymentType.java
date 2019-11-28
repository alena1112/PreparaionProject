package com.alena.preparationproject.mvc.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum PaymentType {
    TRANSFER_TO_BANK_CARD("transferToBankCard", "Перевод на карту банка"),
    CASH("cash", "Наличными при получении");

    private String id;
    private String name;

    PaymentType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static PaymentType fromId(String id) {
        if (StringUtils.isNotBlank(id)) {
            Optional<PaymentType> optional = Arrays.stream(PaymentType.values())
                    .filter(type -> type.getId().equals(id))
                    .findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }
}
