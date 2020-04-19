package com.alena.jewelryproject.model;

import com.alena.jewelryproject.model.enums.PromoCodeType;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Entity
@Table(name = "promotional_code")
public class PromotionalCode extends IdentifiableEntity {
    @Column(name = "code")
    private String code;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "promocode_type")
    private PromoCodeType promoCodeType;

    @Column(name = "value")
    private Double value;

    @Column(name = "max_uses_number")
    private Integer maxUsesNumber;

    @Column(name = "current_uses_number")
    private Integer currentUsesNumber = 0;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "max_jewelries")
    private Integer maxJewelries;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public PromoCodeType getPromoCodeType() {
        return promoCodeType;
    }

    public void setPromoCodeType(PromoCodeType promoCodeType) {
        this.promoCodeType = promoCodeType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getMaxUsesNumber() {
        return maxUsesNumber;
    }

    public void setMaxUsesNumber(Integer maxUsesNumber) {
        this.maxUsesNumber = maxUsesNumber;
    }

    public Integer getCurrentUsesNumber() {
        return currentUsesNumber;
    }

    public void setCurrentUsesNumber(Integer currentUsesNumber) {
        this.currentUsesNumber = currentUsesNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getMaxJewelries() {
        return maxJewelries;
    }

    public void setMaxJewelries(Integer maxJewelries) {
        this.maxJewelries = maxJewelries;
    }
}
