package com.alena.jewelryproject.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum PromoCodeType {
    PERCENT("percent", "Процент"),
    SUM("sum", "Сумма");

    private String id;
    private String name;

    PromoCodeType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static PromoCodeType fromId(String id) {
        if (StringUtils.isNotBlank(id)) {
            Optional<PromoCodeType> optional = Arrays.stream(PromoCodeType.values())
                    .filter(type -> type.getId().equals(id))
                    .findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }
}
