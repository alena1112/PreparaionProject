package com.alena.jewelryproject.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum Country {
    RUSSIA("RUSSIA", "россия"),
    UKRAINE("UKRAINE", "украина"),
    KAZAKHSTAN("KAZAKHSTAN", "казахстан"),
    OTHER("OTHER", "");

    private String id;
    private String name;

    Country(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Country fromId(String id) {
        if (StringUtils.isNotBlank(id)) {
            return Arrays.stream(Country.values())
                    .filter(type -> type.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public static Country fromName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return Arrays.stream(Country.values())
                    .filter(type -> type.getName().contains(StringUtils.lowerCase(name)))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
