package com.alena.preparationproject.web.controller;

import com.alena.preparationproject.web.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/jewelry")
public class JewelryController {
    @Autowired
    private JewelryService jewelryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getAllJewelries() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jewelryList", jewelryService.getAllJewelries());
        modelAndView.setViewName("jewelry_page");
        return modelAndView;
    }

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public ModelAndView getJewelry(@RequestParam(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jewelry", jewelryService.getJewelry(id));
        modelAndView.setViewName("jewelry_item");
        return modelAndView;
    }
}
