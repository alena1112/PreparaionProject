package com.alena.preparationproject.mvc.service;

import com.alena.preparationproject.dao.PromocodeDao;
import com.alena.preparationproject.mvc.model.Jewelry;
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

    public double getDiscount(List<Jewelry> jewelries, PromotionalCode promotionalCode) {
        if (promotionalCode != null && promotionalCode.getId() != null && jewelries != null && !jewelries.isEmpty()) {
            switch (promotionalCode.getPromoCodeType()) {
                case PERCENT:
                    if (promotionalCode.getValue() < 100) {
                        double allJewelriesPrice = getAllJewelriesPrice(jewelries, promotionalCode.getMaxJewelries());
                        return (allJewelriesPrice / 100) * promotionalCode.getValue();
                    }
                case SUM:
                    if (getAllJewelriesPrice(jewelries, null) > promotionalCode.getValue()) {
                        return promotionalCode.getValue();
                    }
            }
        }
        return 0;
    }

    private double getAllJewelriesPrice(List<Jewelry> jewelries, Integer maxPromoJewelries) {
        double allJewelriesPrice;
        if (maxPromoJewelries == null || maxPromoJewelries >= jewelries.size()) {
            allJewelriesPrice = jewelries.stream()
                    .mapToDouble(Jewelry::getPrice)
                    .sum();
        } else {
            allJewelriesPrice = jewelries.stream()
                    .sorted((o1, o2) -> {
                        if (o1.getPrice() < o2.getPrice()) {
                            return 1;
                        } else if (Math.round(o1.getPrice() - o2.getPrice()) == 0) {
                            return 0;
                        } else {
                            return -1;
                        }
                    })
                    .limit(maxPromoJewelries)
                    .mapToDouble(Jewelry::getPrice)
                    .sum();
        }
        return allJewelriesPrice;
    }

    public void deletePromocode(Long id) {
        promocodeDao.delete(id);
    }
}
