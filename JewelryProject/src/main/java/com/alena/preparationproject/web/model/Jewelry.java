package com.alena.preparationproject.web.model;

import com.alena.preparationproject.web.FormatHelper;
import com.alena.preparationproject.web.model.enums.JewelryType;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "jewelry")
public class Jewelry extends IdentifiableEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private JewelryType type;

    @Column(name = "imageUrl")
    private String imageUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "jewelry_material",
            joinColumns = {@JoinColumn(name = "jewelry_id")},
            inverseJoinColumns = {@JoinColumn(name = "material_id")})
    private List<Material> materials;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JewelryType getType() {
        return type;
    }

    public void setType(JewelryType type) {
        this.type = type;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFormatPrice() {
        return FormatHelper.getPriceFormat(price, FormatHelper.Currency.RUB);
    }
}
