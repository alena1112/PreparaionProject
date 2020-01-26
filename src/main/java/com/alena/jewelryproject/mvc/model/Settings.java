package com.alena.jewelryproject.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "settings")
public class Settings extends IdentifiableEntity {
    @Column(name = "s_key")
    private String key;

    @Column(name = "s_value")
    private String value;

    @Column(name = "description")
    private String description;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
