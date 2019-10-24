package com.alena.preparationproject.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/buy")
public class BasketController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllJewelries() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("buy");
        return modelAndView;
    }
}
