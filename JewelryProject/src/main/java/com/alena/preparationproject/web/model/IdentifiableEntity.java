package com.alena.preparationproject.web.model;

import javax.persistence.*;

@MappedSuperclass
public class IdentifiableEntity {
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
