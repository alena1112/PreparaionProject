package com.alena.preparationproject.mvc.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shop")
public class Shop extends IdentifiableEntity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Material> materials;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}
