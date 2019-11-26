package com.alena.preparationproject.web.service;

import com.alena.preparationproject.dao.OrderDao;
import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.model.Order;
import com.alena.preparationproject.web.model.PromotionalCode;
import com.alena.preparationproject.web.model.UserData;
import com.alena.preparationproject.web.model.enums.DeliveryType;
import com.alena.preparationproject.web.model.enums.PaymentType;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final PromoCodeService promoCodeService;
    private final JewelryService jewelryService;
    private final OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao, PromoCodeService promoCodeService, JewelryService jewelryService) {
        this.orderDao = orderDao;
        this.promoCodeService = promoCodeService;
        this.jewelryService = jewelryService;
    }

    public Order createDefaultOrder() {
        Order order = new Order();
        order.setUserData(new UserData());
        order.setJewelries(new ArrayList<>());
        order.setDeliveryType(DeliveryType.RUSSIA_POST_OFFICE);
        order.setPaymentType(PaymentType.TRANSFER_TO_BANK_CARD);
        order.setDeliveryCost(getDeliveryPrice(order.getDeliveryType()));
        order.setTotalCost(getTotalPrice(
                getAllJewelriesPrice(order.getJewelries()),
                0.0,
                order.getDeliveryCost()));

        return order;
    }

    public List<Order> getAllOrders() {
        return orderDao.getAll();
    }

//не должно происходить апдейт в базе по параметрам, украшениям, промокоду
    public void saveOrder(Order order) throws CreateOrderException {
        synchronized (jewelryService) {
            synchronized (promoCodeService) {
                validateOrder(order);

                promoCodeService.applyPromotionCode(order.getPromocode());
                jewelryService.sellJewelries(order.getJewelries());
                orderDao.save(order);
            }
        }
    }

    private void validateOrder(Order order) throws CreateOrderException {
        for (Jewelry orderJewelry : order.getJewelries()) {
            Jewelry jewelry = jewelryService.getJewelry(orderJewelry.getId());
            if (jewelry == null) {
                throw new CreateOrderException(CreateOrderException.ExceptionType.JEWELRY_DOES_NOT_EXIST, orderJewelry);
            }
            if (BooleanUtils.isTrue(jewelry.getSold())) {
                throw new CreateOrderException(CreateOrderException.ExceptionType.JEWELRY_SOLD, orderJewelry);
            }
            if (!jewelry.getPrice().equals(orderJewelry.getPrice())) {
                throw new CreateOrderException(CreateOrderException.ExceptionType.JEWELRY_PRICE_CHANGE, orderJewelry);
            }
        }
        double allJewelriesPrice = getAllJewelriesPrice(order.getJewelries());
        if (order.getPromocode() != null) {
            PromotionalCode promotionalCode = promoCodeService.getPromotionalCode(order.getPromocode().getCode());
            if (promotionalCode == null) {
                throw new CreateOrderException(CreateOrderException.ExceptionType.PROMOCODE_DOES_NOT_EXIST);
            }
            if (!promoCodeService.isValidPromoCode(promotionalCode)) {
                throw new CreateOrderException(CreateOrderException.ExceptionType.PROMOCODE_IS_NOT_VALID);
            }
            if (Math.round(order.getDiscount()) != Math.round(promoCodeService.getDiscount(allJewelriesPrice, promotionalCode))) {
                throw new CreateOrderException(CreateOrderException.ExceptionType.PROMOCODE_IS_CHANGED);
            }
        }
        if (Math.round(order.getDeliveryCost()) != getDeliveryPrice(order.getDeliveryType())) {
            throw new CreateOrderException(CreateOrderException.ExceptionType.DELIVERY_IS_CHANGED);
        }
    }

    public void updateOrderAfterAddJewelry(Order order, Long addedJewelryId) {
        updateOrderAfterChangeJewelry(order, addedJewelryId, JewelryAction.ADD);
    }

    public void updateOrderAfterDeleteJewelry(Order order, Long removedJewelryId) {
        updateOrderAfterChangeJewelry(order, removedJewelryId, JewelryAction.REMOVE);
    }

    private void updateOrderAfterChangeJewelry(Order order, Long jewelryId, JewelryAction action) {
        if (action == JewelryAction.REMOVE) {
            order.getJewelries().stream()
                    .filter(jewelry -> jewelry.getId().equals(jewelryId))
                    .findFirst()
                    .ifPresent(foundJewelry -> order.getJewelries().remove(foundJewelry));
        } else {
            Jewelry jewelry = jewelryService.getJewelry(jewelryId);
            if (jewelry != null) {
                order.getJewelries().add(jewelry);
            }
        }

        double allJewelriesPrice = getAllJewelriesPrice(order.getJewelries());
        order.setDiscount(promoCodeService.getDiscount(allJewelriesPrice, order.getPromocode()));
        order.setTotalCost(getTotalPrice(
                allJewelriesPrice,
                order.getDiscount(),
                order.getDeliveryCost())
        );
    }

    public void updateOrderAfterChangeDeliveryType(Order order, String deliveryType) {
        DeliveryType type = DeliveryType.fromId(deliveryType);
        order.setDeliveryType(type);
        order.setDeliveryCost(getDeliveryPrice(type));
        order.setTotalCost(getTotalPrice(
                getAllJewelriesPrice(order.getJewelries()),
                order.getDiscount(),
                order.getDeliveryCost())
        );
    }

    public void updateOrderAfterAddPromoCode(Order order, String promocode) {
        PromotionalCode promotionalCode = promoCodeService.getPromotionalCode(promocode);
        if (promotionalCode != null && promoCodeService.isValidPromoCode(promotionalCode)) {
            order.setPromocode(promotionalCode);
            double allJewelriesPrice = getAllJewelriesPrice(order.getJewelries());
            order.setDiscount(promoCodeService.getDiscount(allJewelriesPrice, promotionalCode));
            order.setTotalCost(getTotalPrice(
                    allJewelriesPrice,
                    order.getDiscount(),
                    order.getDeliveryCost())
            );
        } else {
            updateOrderForInvalidPromoCode(order);
        }
    }

    public void updateOrderForInvalidPromoCode(Order order) {
        order.setPromocode(null);
        order.setDiscount(0.0);
        order.setTotalCost(getTotalPrice(
                getAllJewelriesPrice(order.getJewelries()),
                order.getDiscount(),
                order.getDeliveryCost())
        );
    }

    private double getTotalPrice(Double allJewelriesPrice, Double discount, Double delivery) {
        return ObjectUtils.defaultIfNull(allJewelriesPrice, 0.0) -
                ObjectUtils.defaultIfNull(discount, 0.0) +
                ObjectUtils.defaultIfNull(delivery, 0.0);
    }

    private double getAllJewelriesPrice(List<Jewelry> jewelries) {
        double allJewelriesPrice = 0;
        if (jewelries != null) {
            allJewelriesPrice = jewelries.stream()
                    .mapToDouble(Jewelry::getPrice)
                    .sum();
        }
        return allJewelriesPrice;
    }

    private double getDeliveryPrice(DeliveryType deliveryType) {
        if (deliveryType == DeliveryType.RUSSIA_POST_OFFICE) {
            //TODO
            return 200;
        }
        return 0;
    }

    private enum JewelryAction {ADD, REMOVE}
}
