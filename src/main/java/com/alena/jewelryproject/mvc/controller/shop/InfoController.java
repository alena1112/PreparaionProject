package com.alena.jewelryproject.mvc.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InfoController {

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView about() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop/about");
        return modelAndView;
    }

    @RequestMapping(value = "/delivery", method = RequestMethod.GET)
    public ModelAndView delivery() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop/delivery");
        return modelAndView;
    }

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public ModelAndView payment() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop/payment");
        return modelAndView;
    }

    @RequestMapping(value = "/return", method = RequestMethod.GET)
    public ModelAndView returnInfo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop/return");
        return modelAndView;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public ModelAndView contacts() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop/contacts");
        return modelAndView;
    }
}
