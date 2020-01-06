package com.alena.preparationproject.mvc.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image extends IdentifiableEntity {
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jewelry_id")
    private Jewelry jewelry;

    /*
    * 0 - main image
    */
    @Column(name = "img_index")
    private Integer index;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Jewelry getJewelry() {
        return jewelry;
    }

    public void setJewelry(Jewelry jewelry) {
        this.jewelry = jewelry;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
