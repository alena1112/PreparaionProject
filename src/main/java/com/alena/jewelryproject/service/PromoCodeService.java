package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.PromocodeRepository;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.PromotionalCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoCodeService {
    @Autowired
    private PromocodeRepository promocodeRepository;

    public PromotionalCode getPromotionalCode(String code) {
        return promocodeRepository.getByCode(code);
    }

    public PromotionalCode getPromotionalCodeById(Long id) {
        return promocodeRepository.findById(id).orElse(null);
    }

    public List<PromotionalCode> getAllPromocodes() {
        return promocodeRepository.findAll();
    }

    public void save(PromotionalCode promotionalCode) {
        promocodeRepository.save(promotionalCode);
    }

    public synchronized void update(PromotionalCode promotionalCode) {
        promocodeRepository.save(promotionalCode);
    }

    public synchronized void delete(Long id) {
        promocodeRepository.deleteById(id);
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

    public synchronized boolean applyPromotionCode(PromotionalCode promotionalCode) {
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
            promocodeRepository.save(promotionalCode);
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

    private static double getAllJewelriesPrice(List<Jewelry> jewelries, Integer maxPromoJewelries) {
        double allJewelriesPrice;
        if (maxPromoJewelries == null || maxPromoJewelries == 0 || maxPromoJewelries >= jewelries.size()) {
            allJewelriesPrice = jewelries.stream()
                    .mapToDouble(Jewelry::getPrice)
                    .sum();
        } else {
            allJewelriesPrice = jewelries.stream()
                    .sorted((o1, o2) -> {
                        if (o1.getPrice() > o2.getPrice()) {
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
}
