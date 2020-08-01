package com.alena.jewelryproject.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum DeliveryType {
    POST_OFFICE("postOffice", "Почта"),
    PICKUP("pickup", "Самовывоз"),
    BOXBERRY_MOSCOW("boxberryMoscow", "Boxberry");

    private String id;
    private String name;

    DeliveryType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static DeliveryType fromId(String id) {
        if (StringUtils.isNotBlank(id)) {
            Optional<DeliveryType> optional = Arrays.stream(DeliveryType.values())
                    .filter(type -> type.getId().equals(id))
                    .findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }
}
