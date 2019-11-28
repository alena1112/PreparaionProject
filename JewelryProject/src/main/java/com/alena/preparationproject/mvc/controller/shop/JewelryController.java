package com.alena.preparationproject.mvc.controller.shop;

import com.alena.preparationproject.mvc.model.Jewelry;
import com.alena.preparationproject.mvc.model.Order;
import com.alena.preparationproject.mvc.service.JewelryService;
import com.alena.preparationproject.mvc.service.OrderService;
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
    public ModelAndView getJewelry(@RequestParam(value = "id") Long id,
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
        orderService.updateOrderAfterAddJewelry(order, id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("order", order);
        modelAndView.setViewName("redirect:/jewelry?id=" + id);
        return modelAndView;
    }

    @ModelAttribute("order")
    public Order createOrder() {
        return orderService.createDefaultOrder();
    }
}
