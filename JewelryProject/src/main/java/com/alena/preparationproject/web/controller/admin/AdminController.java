package com.alena.preparationproject.web.controller.admin;

import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.model.enums.JewelryType;
import com.alena.preparationproject.web.service.JewelryService;
import com.alena.preparationproject.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/jewelry/list", method = RequestMethod.GET)
    public ModelAndView getAllJewelries() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jewelryList", jewelryService.getAllJewelries());
        modelAndView.setViewName("admin/jewelry_list");
        return modelAndView;
    }

    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public ModelAndView getAllOrders() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orderList", orderService.getAllOrders());
        modelAndView.setViewName("admin/order_list");
        return modelAndView;
    }

    @RequestMapping(value = "/jewelry/edit", method = RequestMethod.GET)
    public ModelAndView editJewelry(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jewelryItem", id != null ? jewelryService.getJewelry(id) : new Jewelry());
        modelAndView.addObject("jewelryTypes", JewelryType.values());
        modelAndView.setViewName("admin/jewelry_edit");
        return modelAndView;
    }

    @RequestMapping(value = "/jewelry/save", method = RequestMethod.POST)
    public ModelAndView saveJewelry(@RequestParam(value = "id") Long id, @ModelAttribute("jewelryItem") Jewelry jewelry) {
        if (id == null) {
            jewelryService.saveNew(jewelry);
        } else {
            jewelry.setId(id);
            jewelryService.save(jewelry);
        }
        return new ModelAndView("redirect:/jewelry/list", new HashMap<>());
    }

    @RequestMapping(value = "/jewelry/delete", method = RequestMethod.GET)
    public ModelAndView deleteJewelry(@RequestParam(value = "id") Long id) {
        jewelryService.deleteJewelry(id);
        return new ModelAndView("redirect:/jewelry/list", new HashMap<>());
    }
}
