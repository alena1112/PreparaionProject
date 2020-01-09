package com.alena.preparationproject.mvc.service;

import com.alena.preparationproject.dao.PromocodeDao;
import com.alena.preparationproject.mvc.model.PromotionalCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoCodeService {
    @Autowired
    private PromocodeDao promocodeDao;

    public PromotionalCode getPromotionalCode(String code) {
        return promocodeDao.getByCode(code);
    }

    public PromotionalCode getPromotionalCodeById(Long id) {
        return promocodeDao.get(id).orElse(null);
    }

    public List<PromotionalCode> getAllPromocodes() {
        return promocodeDao.getAll();
    }

    public void save(PromotionalCode promotionalCode) {
        promocodeDao.save(promotionalCode);
    }

    public void update(PromotionalCode promotionalCode) {
        promocodeDao.update(promotionalCode);
    }

    public void delete(PromotionalCode promotionalCode) {
        promocodeDao.delete(promotionalCode.getId());
    }

    public boolean isValidPromoCode(PromotionalCode promocode) {
        if (!promocode.getActive()) {
            return false;
        }
        if (promocode.getMaxUsesNumber() != null &&
                promocode.getCurrentUsesNumber() >= promocode.getMaxUsesNumber()) {
            return false;
        }
        if (promocode.getExpirationDate() != null &&
                System.currentTimeMillis() > promocode.getExpirationDate().getTime()) {
            return false;
        }
        return true;
    }

    public boolean applyPromotionCode(PromotionalCode promotionalCode) {
        if (promotionalCode != null) {
            promotionalCode.setCurrentUsesNumber(promotionalCode.getCurrentUsesNumber() + 1);
            if (promotionalCode.getMaxUsesNumber() != null &&
                    promotionalCode.getMaxUsesNumber().equals(promotionalCode.getCurrentUsesNumber())) {
                promotionalCode.setActive(false);
            }
            if (promotionalCode.getExpirationDate() != null &&
                    System.currentTimeMillis() > promotionalCode.getExpirationDate().getTime()) {
                promotionalCode.setActive(false);
            }
            promocodeDao.save(promotionalCode);
            return true;
        }
        return false;
    }

    public double getDiscount(double allJewelriesPrice, PromotionalCode promotionalCode) {
        if (promotionalCode != null && promotionalCode.getId() != null && Math.round(allJewelriesPrice) != 0) {
            switch (promotionalCode.getPromoCodeType()) {
                case PERCENT:
                    if (promotionalCode.getValue() < 100) {
                        return (allJewelriesPrice / 100) * promotionalCode.getValue();
                    }
                case SUM:
                    if (allJewelriesPrice > promotionalCode.getValue()) {
                        return promotionalCode.getValue();
                    }
            }
        }
        return 0;
    }

    public void deletePromocode(Long id) {
        promocodeDao.delete(id);
    }
}
