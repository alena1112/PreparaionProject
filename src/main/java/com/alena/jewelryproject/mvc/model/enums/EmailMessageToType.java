package com.alena.jewelryproject.mvc.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum EmailMessageToType {
    CLIENT("client", "Клиент"),
    ADMIN("admin", "Админ");

    private String id;
    private String name;

    EmailMessageToType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static EmailMessageToType fromId(String id) {
        if (StringUtils.isNotBlank(id)) {
            Optional<EmailMessageToType> optional = Arrays.stream(EmailMessageToType.values())
                    .filter(type -> type.getId().equals(id))
                    .findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }
}
