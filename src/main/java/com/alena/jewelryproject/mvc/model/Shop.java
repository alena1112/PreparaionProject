package com.alena.jewelryproject.mvc.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shop")
public class Shop extends IdentifiableEntity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<MaterialOrder> orders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MaterialOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<MaterialOrder> orders) {
        this.orders = orders;
    }
}
