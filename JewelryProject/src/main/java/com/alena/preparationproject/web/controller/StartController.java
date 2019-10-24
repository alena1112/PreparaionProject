package com.alena.preparationproject.web.controller;

import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.model.JewelryType;
import com.alena.preparationproject.web.service.JewelryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/start")
public class StartController {
    @Autowired
    private JewelryService jewelryService;

    private static final String MENU_ALL = "all";
    private static final String MENU_NEW = "new";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllJewelries(@RequestParam(value = "menu") String menu) {
        ModelAndView modelAndView = new ModelAndView();
        List<Jewelry> allJewelries = getJewelries(menu);
        List<List<Jewelry>> list = new ArrayList<>();
        for (int i = 0; i < allJewelries.size(); i += 4) {
            int end = allJewelries.size() >= i + 4 ? i + 4 : allJewelries.size();
            list.add(allJewelries.subList(i, end));
        }
        modelAndView.addObject("jewelryList", list);
        modelAndView.setViewName("start");
        return modelAndView;
    }

    private List<Jewelry> getJewelries(String menu) {
        if (StringUtils.isBlank(menu) || MENU_ALL.equals(menu)) {
            return jewelryService.getAllJewelries();
        } else if (MENU_NEW.equals(menu)) {
            return jewelryService.getNewJewelries(new Date(), 10);
        } else {
            JewelryType jewelryType = JewelryType.fromId(menu);
            if (jewelryType != null) {
                return jewelryService.getJewelries(jewelryType);
            }
        }
        return new ArrayList<>();
    }
}
