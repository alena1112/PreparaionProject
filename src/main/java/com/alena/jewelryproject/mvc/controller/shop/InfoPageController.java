package com.alena.jewelryproject.mvc.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = "order")
@RequestMapping("/info")
public class InfoPageController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getJewelry(@RequestParam(value = "type") InfoPageType type) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("type", type);
        modelAndView.setViewName("shop/info");
        return modelAndView;
    }
}
