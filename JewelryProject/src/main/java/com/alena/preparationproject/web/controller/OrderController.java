package com.alena.preparationproject.web.controller;

import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.model.Order;
import com.alena.preparationproject.web.model.PromotionalCode;
import com.alena.preparationproject.web.model.UserData;
import com.alena.preparationproject.web.model.enums.DeliveryType;
import com.alena.preparationproject.web.model.enums.JewelryType;
import com.alena.preparationproject.web.model.enums.PaymentType;
import com.alena.preparationproject.web.service.JewelryService;
import com.alena.preparationproject.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@SessionAttributes(value = "order")
@RequestMapping("/buy")
public class OrderController {
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllJewelries(@ModelAttribute("order") Order order) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("buy");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ModelAndView createOrder(@ModelAttribute("order") Order order) {
        ModelAndView modelAndView = new ModelAndView("redirect:/buy");
        modelAndView.addObject("order", createOrder());
        return modelAndView;
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public ModelAndView deleteItem(@ModelAttribute("order") Order order,
                                   @RequestParam("itemId") Long jewelryId) {
        order.getJewelries().stream()
                .filter(jewelry -> jewelry.getId().equals(jewelryId))
                .findFirst()
                .ifPresent(foundJewelry -> order.getJewelries().remove(foundJewelry));

        double allJewelriesPrice = orderService.getAllJewelriesPrice(order.getJewelries());
        order.setDiscount(orderService.getDiscount(allJewelriesPrice, order.getPromocode()));
        order.setTotalCost(orderService.getTotalPrice(
                allJewelriesPrice,
                order.getDiscount(),
                order.getDeliveryCost())
        );

        ModelAndView modelAndView = new ModelAndView("redirect:/buy");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @RequestMapping(value = "/checkPromoCode", method = RequestMethod.GET)
    public ModelAndView checkPromoCode(@ModelAttribute("order") Order order,
                                       @RequestParam("code") String code) {
        ModelAndView modelAndView = new ModelAndView("redirect:/buy#promocode");
        modelAndView.addObject("order", order);

        PromotionalCode promotionalCode = orderService.getPromotionalCode(code);
        if (promotionalCode != null && orderService.isValidPromoCode(promotionalCode)) {
            order.setPromocode(promotionalCode);
            double allJewelriesPrice = orderService.getAllJewelriesPrice(order.getJewelries());
            order.setDiscount(orderService.getDiscount(allJewelriesPrice, promotionalCode));
            order.setTotalCost(orderService.getTotalPrice(
                    allJewelriesPrice,
                    order.getDiscount(),
                    order.getDeliveryCost())
            );
            order.setPromocodeMessage("Промокод успешно применен!");
        } else {
            order.setPromocode(null);
            order.setDiscount(0.0);
            order.setTotalCost(orderService.getTotalPrice(
                    orderService.getAllJewelriesPrice(order.getJewelries()),
                    order.getDiscount(),
                    order.getDeliveryCost())
            );
            order.setPromocodeMessage("Промокод не был найдет или не активен");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/checkDelivery", method = RequestMethod.GET)
    public ModelAndView checkDelivery(@ModelAttribute("order") Order order,
                                      @RequestParam("type") String type) {
        ModelAndView modelAndView = new ModelAndView("redirect:/buy#delivery");
        modelAndView.addObject("order", order);

        DeliveryType deliveryType = DeliveryType.fromId(type);
        order.setDeliveryType(deliveryType);
        order.setDeliveryCost(orderService.getDeliveryPrice(deliveryType));
        order.setTotalCost(orderService.getTotalPrice(
                orderService.getAllJewelriesPrice(order.getJewelries()),
                order.getDiscount(),
                order.getDeliveryCost())
        );
        return modelAndView;
    }

    @ModelAttribute("order")
    public Order createOrder() {
        Order order = new Order();
        order.setUserData(new UserData());
        order.setDeliveryType(DeliveryType.RUSSIA_POST_OFFICE);
        order.setPaymentType(PaymentType.TRANSFER_TO_BANK_CARD);
        //TODO убрать!!!
        List<Jewelry> jewelries = jewelryService.getJewelries(JewelryType.BRACELET);
        order.setJewelries(jewelries);

        order.setDeliveryCost(orderService.getDeliveryPrice(order.getDeliveryType()));
        order.setTotalCost(orderService.getTotalPrice(
                orderService.getAllJewelriesPrice(order.getJewelries()),
                0.0,
                order.getDeliveryCost()));

        return order;
    }
}
