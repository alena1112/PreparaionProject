package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.OrderRepository;
import com.alena.jewelryproject.model.*;
import com.alena.jewelryproject.model.enums.DeliveryType;
import com.alena.jewelryproject.model.enums.PaymentType;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.alena.jewelryproject.service.SettingKeys.DELIVERY_COST_RUSSIA_POST_OFFICE;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final PromoCodeService promoCodeService;
    private final JewelryService jewelryService;
    private final SettingsService settingsService;
    private final EmailMessagesService emailMessagesService;

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, PromoCodeService promoCodeService, JewelryService jewelryService,
                        SettingsService settingsService, EmailMessagesService emailMessagesService) {
        this.orderRepository = orderRepository;
        this.promoCodeService = promoCodeService;
        this.jewelryService = jewelryService;
        this.settingsService = settingsService;
        this.emailMessagesService = emailMessagesService;
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

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    //не должно происходить апдейт в базе по параметрам, украшениям, промокоду
    public void saveOrder(Order order) throws CreateOrderException {
        if (jewelryService.hashCode() > jewelryService.hashCode()) {
            synchronized (jewelryService) {
                synchronized (promoCodeService) {//applyPromotionCode может быть вызван другим потоком не в synch блоке
                    validateOrder(order);

                    promoCodeService.applyPromotionCode(order.getPromocode());
                    jewelryService.sellJewelries(order.getJewelries());
                    orderRepository.save(order);
                    sendEmailMessages(order);
                }
            }
        } else {
            synchronized (promoCodeService) {
                synchronized (jewelryService) {
                    validateOrder(order);

                    promoCodeService.applyPromotionCode(order.getPromocode());
                    jewelryService.sellJewelries(order.getJewelries());
                    orderRepository.save(order);
                    sendEmailMessages(order);
                }
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
        if (order.getPromocode() != null) {
            PromotionalCode promotionalCode = promoCodeService.getPromotionalCode(order.getPromocode().getCode());
            if (promotionalCode == null) {
                throw new CreateOrderException(CreateOrderException.ExceptionType.PROMOCODE_DOES_NOT_EXIST);
            }
            if (!promoCodeService.isValidPromoCode(promotionalCode)) {
                throw new CreateOrderException(CreateOrderException.ExceptionType.PROMOCODE_IS_NOT_VALID);
            }
            if (Math.round(order.getDiscount()) != Math.round(promoCodeService.getDiscount(order.getJewelries(), promotionalCode))) {
                throw new CreateOrderException(CreateOrderException.ExceptionType.PROMOCODE_IS_CHANGED);
            }
        }
        if (Math.round((Double) ObjectUtils.defaultIfNull(order.getDeliveryCost(), 0)) !=
                getDeliveryPrice(order.getDeliveryType())) {
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
        order.setDiscount(promoCodeService.getDiscount(order.getJewelries(), order.getPromocode()));
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
            order.setDiscount(promoCodeService.getDiscount(order.getJewelries(), promotionalCode));
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

    private static double getTotalPrice(Double allJewelriesPrice, Double discount, Double delivery) {
        return ObjectUtils.defaultIfNull(allJewelriesPrice, 0.0) -
                ObjectUtils.defaultIfNull(discount, 0.0) +
                ObjectUtils.defaultIfNull(delivery, 0.0);
    }

    private static double getAllJewelriesPrice(List<Jewelry> jewelries) {
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
            String value = settingsService.getSettingByKey(DELIVERY_COST_RUSSIA_POST_OFFICE, "50");
            return Double.parseDouble(value);
        }
        return 0;
    }

    private void sendEmailMessages(Order order) {
        String adminEmailId = settingsService.getSettingByKey(SettingKeys.NEW_ORDER_ADMIN_EMAIL_ID);
        String clientEmailId = settingsService.getSettingByKey(SettingKeys.NEW_ORDER_CLIENT_EMAIL_ID);
        if (StringUtils.isNotBlank(adminEmailId)) {
            emailMessagesService.sendEmail(Long.parseLong(adminEmailId), order);
        }
        if (StringUtils.isNotBlank(clientEmailId)) {
            emailMessagesService.sendEmail(Long.parseLong(adminEmailId), order);
        }
    }

    private enum JewelryAction {ADD, REMOVE}
}
