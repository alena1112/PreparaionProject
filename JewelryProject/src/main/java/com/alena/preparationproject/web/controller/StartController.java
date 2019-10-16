package com.alena.preparationproject.web.controller;

import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/start")
public class StartController {
    @Autowired
    private JewelryService jewelryService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllJewelries() {
        ModelAndView modelAndView = new ModelAndView();
        List<Jewelry> allJewelries = jewelryService.getAllJewelries();
        int size = allJewelries.size();
        if (size > 4) {
            modelAndView.addObject("jewelryListRow1", allJewelries.subList(0, 4));
            modelAndView.addObject("jewelryListRow2", allJewelries.subList(4, size > 8 ? 8 : size));
        } else {
            modelAndView.addObject("jewelryListRow1", allJewelries);
            modelAndView.addObject("jewelryListRow2", new ArrayList<>());
        }
        modelAndView.setViewName("start");
        return modelAndView;
    }
}
