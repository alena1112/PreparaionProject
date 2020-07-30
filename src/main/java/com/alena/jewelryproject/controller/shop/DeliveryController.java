package com.alena.jewelryproject.controller.shop;

import com.alena.jewelryproject.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.alena.jewelryproject.service.SettingKeys.DELIVERY_COST_RUSSIA_POST_OFFICE;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private SettingsService settingsService;

    @GetMapping
    public ModelAndView getDeliveryInfo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("deliveryCost", settingsService.getSettingByKey(DELIVERY_COST_RUSSIA_POST_OFFICE));
        modelAndView.setViewName("shop/info/delivery");
        return modelAndView;
    }
}
