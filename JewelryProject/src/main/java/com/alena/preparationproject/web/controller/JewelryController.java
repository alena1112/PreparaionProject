package com.alena.preparationproject.web.controller;

import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView getJewelry(/*@RequestParam(value = "id") Long id*/) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jewelry_item");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveJewelry(@ModelAttribute("jewelry") Jewelry jewelry) {
        if (jewelry.getId() == null) {
            jewelryService.saveNew(jewelry);
        } else {
//            jewelryService.save();
        }
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView("redirect:/jewelry/list", map);
    }
}
