package com.alena.jewelryproject.model;

import javax.persistence.*;

@Entity
@Table(name = "jewelry_material")
public class JewelryMaterial extends IdentifiableEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jewelry_id")
    private Jewelry jewelry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "number")
    private Integer number;

    public Jewelry getJewelry() {
        return jewelry;
    }

    public void setJewelry(Jewelry jewelry) {
        this.jewelry = jewelry;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
