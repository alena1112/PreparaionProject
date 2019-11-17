package com.alena.preparationproject.web.controller.admin;

import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.model.enums.JewelryType;
import com.alena.preparationproject.web.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private JewelryService jewelryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getAllJewelries() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jewelryList", jewelryService.getAllJewelries());
        modelAndView.setViewName("jewelry_list");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editJewelry(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jewelryItem", id != null ? jewelryService.getJewelry(id) : new Jewelry());
        modelAndView.addObject("jewelryTypes", JewelryType.values());
        modelAndView.setViewName("jewelry_edit");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveJewelry(@RequestParam(value = "id") Long id, @ModelAttribute("jewelryItem") Jewelry jewelry) {
        if (id == null) {
            jewelryService.saveNew(jewelry);
        } else {
            jewelry.setId(id);
            jewelryService.save(jewelry);
        }
        return new ModelAndView("redirect:/jewelry/list", new HashMap<>());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteJewelry(@RequestParam(value = "id") Long id) {
        jewelryService.deleteJewelry(id);
        return new ModelAndView("redirect:/jewelry/list", new HashMap<>());
    }
}
