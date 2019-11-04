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

        ModelAndView modelAndView = new ModelAndView("redirect:/buy");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @RequestMapping(value = "/checkPromoCode", method = RequestMethod.GET)
    public ModelAndView checkPromoCode(@ModelAttribute("order") Order order,
                                       @RequestParam("code") String code) {
        ModelAndView modelAndView = new ModelAndView("redirect:/buy");
        modelAndView.addObject("order", order);
        if (orderService.isValidPromoCode(code)) {

        } else {

        }
        return modelAndView;
    }

    @ModelAttribute("order")
    public Order createOrder() {
        Order order = new Order();
        order.setUserData(new UserData());
        order.setDeliveryType(DeliveryType.RUSSIA_POST_OFFICE);
        order.setPaymentType(PaymentType.TRANSFER_TO_BANK_CARD);
        order.setPromocode(new PromotionalCode());
        //TODO убрать!!!
        List<Jewelry> jewelries = jewelryService.getJewelries(JewelryType.BRACELET);
        order.setJewelries(jewelries);

        return order;
    }
}
