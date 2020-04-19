package com.alena.jewelryproject.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class IdentifiableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
