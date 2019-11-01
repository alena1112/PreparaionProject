package com.alena.preparationproject.web.model;

import com.alena.preparationproject.web.model.enums.PromoCodeType;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Entity
@Table(name = "promotional_code")
public class PromotionalCode extends IdentifiableEntity {
    @Column(name = "code")
    private String code;

    @Column(name = "is_active")
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "promocode_type")
    private PromoCodeType promoCodeType;

    @Column(name = "value")
    private Double value;

    @Column(name = "max_uses_number")
    private Integer maxUsesNumber;

    @Column(name = "current_uses_number")
    private Integer currentUsesNumber;

    @Column(name = "expiration_date")
    private Date expirationDate;

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
}
