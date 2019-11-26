package com.alena.preparationproject.web.controller.shop;

import com.alena.preparationproject.web.model.Order;
import com.alena.preparationproject.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = "order")
@RequestMapping("/info")
public class InfoPageController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getJewelry(@RequestParam(value = "type") InfoPageType type) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("type", type);
        modelAndView.setViewName("shop/info");
        return modelAndView;
    }

    @ModelAttribute("order")
    public Order createOrder() {
        return orderService.createDefaultOrder();
    }
}
