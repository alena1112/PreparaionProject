package com.alena.preparationproject.web.service;

import com.alena.preparationproject.dao.PromocodeDao;
import com.alena.preparationproject.web.model.PromotionalCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private PromocodeDao promocodeDao;

    public boolean isValidPromoCode(String code) {
        PromotionalCode promocode = promocodeDao.getByCode(code);
        if (promocode == null) {
            return false;
        }
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
}
