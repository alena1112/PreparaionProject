package com.alena.jewelryproject.model;

import com.alena.jewelryproject.FormatHelper;
import com.alena.jewelryproject.model.enums.JewelryType;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
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

    @OneToMany(mappedBy = "jewelry", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Image> images;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "jewelry_material",
            joinColumns = {@JoinColumn(name = "jewelry_id")},
            inverseJoinColumns = {@JoinColumn(name = "material_id")})
    private List<Material> materials;

    @Column(name = "material_description")
    private String materialDescription;

    @Column(name = "size")
    private String size;

    @Column(name = "weight")
    private String weight;

    @Column(name = "is_sold")
    private Boolean isSold = false;

    @Column(name = "is_hide")
    private Boolean isHide = false;

    @Column(name = "created_date")
    private Date createdDate = new Date(System.currentTimeMillis());

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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Image getImage(int index) {
        return images != null && images.size() > index ?
                images.stream()
                        .filter(image -> image.getIndex() == index)
                        .findFirst()
                        .orElse(null) :
                null;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Boolean getSold() {
        return isSold;
    }

    public void setSold(Boolean sold) {
        isSold = sold;
    }

    public Boolean getHide() {
        return isHide;
    }

    public void setHide(Boolean hide) {
        isHide = hide;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getFormatPrice() {
        return FormatHelper.getPriceFormat(price, FormatHelper.Currency.RUB);
    }
}
