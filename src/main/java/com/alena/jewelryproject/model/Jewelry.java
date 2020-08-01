package com.alena.jewelryproject.model;

import com.alena.jewelryproject.FormatHelper;
import com.alena.jewelryproject.model.enums.JewelryType;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "jewelry")
public class Jewelry extends IdentifiableEntity {
    @Expose
    @Column(name = "name")
    private String name;

    @Expose
    @Column(name = "original_price")
    private Double originalPrice;

    @Expose
    @Column(name = "price")
    private Double price;

    @Expose
    @Column(name = "description")
    private String description;

    @Expose
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private JewelryType type;

    @Expose
    @OneToMany(mappedBy = "jewelry", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "jewelry_material",
            joinColumns = {@JoinColumn(name = "jewelry_id")},
            inverseJoinColumns = {@JoinColumn(name = "material_id")})
    private List<Material> materials;

    @Expose
    @Column(name = "material_description")
    private String materialDescription;

    @Expose
    @Column(name = "size")
    private String size;

    @Expose
    @Column(name = "weight")
    private String weight;

    @Expose
    @Column(name = "is_sold")
    private Boolean isSold = false;

    @Expose
    @Column(name = "is_hide")
    private Boolean isHide = false;

    @Column(name = "created_date")
    private Date createdDate = new Date(System.currentTimeMillis());

    @Column(name = "rating")
    private Integer rating;

    public Image getImage(int index) {
        return images != null && !images.isEmpty() ?
                images.stream()
                        .filter(image -> image.getIndex() == index)
                        .findFirst()
                        .orElse(null) :
                null;
    }

    public String getFormatPrice() {
        return FormatHelper.getPriceFormat(price, FormatHelper.Currency.RUB);
    }
}
