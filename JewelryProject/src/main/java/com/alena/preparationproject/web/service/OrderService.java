package com.alena.preparationproject.web.service;

import com.alena.preparationproject.dao.PromocodeDao;
import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.model.Order;
import com.alena.preparationproject.web.model.PromotionalCode;
import com.alena.preparationproject.web.model.enums.DeliveryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private PromocodeDao promocodeDao;

    public PromotionalCode getPromotionalCode(String code) {
        return promocodeDao.getByCode(code);
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

    public double getTotalPrice(double allJewelriesPrice, double discount, double delivery) {
        return allJewelriesPrice - discount + delivery;
    }

    public double getAllJewelriesPrice(List<Jewelry> jewelries) {
        double allJewelriesPrice = 0;
        if (jewelries != null) {
            allJewelriesPrice = jewelries.stream()
                    .mapToDouble(Jewelry::getPrice)
                    .sum();
        }
        return allJewelriesPrice;
    }

    public double getDeliveryPrice(DeliveryType deliveryType) {
        if (deliveryType == DeliveryType.RUSSIA_POST_OFFICE) {
            //TODO
            return 200;
        }
        return 0;
    }

    public double getDiscount(double allJewelriesPrice, PromotionalCode promotionalCode) {
        if (promotionalCode != null && promotionalCode.getId() != null) {
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
}
