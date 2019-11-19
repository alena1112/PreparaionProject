package com.alena.preparationproject.web.controller;

import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.model.Order;
import com.alena.preparationproject.web.model.UserData;
import com.alena.preparationproject.web.model.enums.DeliveryType;
import com.alena.preparationproject.web.model.enums.PaymentType;
import com.alena.preparationproject.web.service.JewelryService;
import com.alena.preparationproject.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.function.Predicate;

@Controller
@RequestMapping("/jewelry")
@SessionAttributes(value = "order")
public class JewelryController {
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView editJewelry(@RequestParam(value = "id") Long id,
                                    @ModelAttribute("order") Order order) {
        ModelAndView modelAndView = new ModelAndView();
        Jewelry jewelry = jewelryService.getJewelry(id);
        modelAndView.addObject("jewelry", jewelry);
        modelAndView.addObject("order", order);
        modelAndView.addObject("isContains",
                order.getJewelries()
                        .stream()
                        .anyMatch(j -> j.getId().equals(jewelry.getId()))
        );
        modelAndView.setViewName("shop/jewelry");
        return modelAndView;
    }

    @RequestMapping(value = "/addInOrder", method = RequestMethod.POST)
    public ModelAndView addInOrder(@RequestParam(value = "id") Long id,
                                   @ModelAttribute("order") Order order) {
        Jewelry jewelry = jewelryService.getJewelry(id);
        if (jewelry != null) {
            order.getJewelries().add(jewelry);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("order", order);
        modelAndView.setViewName("redirect:/jewelry?id=" + id);
        return modelAndView;
    }

    @ModelAttribute("order")
    public Order createOrder() {
        Order order = new Order();
        order.setUserData(new UserData());
        order.setJewelries(new ArrayList<>());
        order.setDeliveryType(DeliveryType.RUSSIA_POST_OFFICE);
        order.setPaymentType(PaymentType.TRANSFER_TO_BANK_CARD);
        order.setDeliveryCost(orderService.getDeliveryPrice(order.getDeliveryType()));
        order.setTotalCost(orderService.getTotalPrice(
                orderService.getAllJewelriesPrice(order.getJewelries()),
                0.0,
                order.getDeliveryCost()));

        return order;
    }
}
