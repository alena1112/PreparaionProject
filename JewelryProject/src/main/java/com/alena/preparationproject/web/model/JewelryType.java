package com.alena.preparationproject.web.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum JewelryType {
    EARRINGS("earrings", "Серьги"),
    BRACELET("bracelet", "Браслет"),
    NECKLACE("necklace", "Колье");

    private String id;
    private String name;

    JewelryType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static JewelryType fromId(String id) {
        if (StringUtils.isNotBlank(id)) {
            Optional<JewelryType> optional = Arrays.stream(JewelryType.values())
                    .filter(type -> type.getId().equals(id))
                    .findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }
}
