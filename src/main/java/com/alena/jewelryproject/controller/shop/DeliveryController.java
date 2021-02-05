package com.alena.jewelryproject.controller.shop;

import com.alena.jewelryproject.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.alena.jewelryproject.service.SettingKeys.*;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private SettingsService settingsService;

    @GetMapping
    public ModelAndView getDeliveryInfo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("deliveryPickupAvailableMoscow", settingsService.getSettingByKey(DELIVERY_PICKUP_AVAILABLE_MOSCOW));
        modelAndView.addObject("deliveryPickupAvailableSamara", settingsService.getSettingByKey(DELIVERY_PICKUP_AVAILABLE_SAMARA));
        String russiaPostOfficeDelivery = settingsService.getSettingByKey(DELIVERY_COST_RUSSIA_POST_OFFICE);
        modelAndView.addObject("deliveryCost", russiaPostOfficeDelivery);
        modelAndView.addObject("deliveryFree", SettingsService.isFree(russiaPostOfficeDelivery));
        String boxberryMoscowDeliveryCost = settingsService.getSettingByKey(BOXBERRY_MOSCOW_DELIVERY_COST);
        modelAndView.addObject("boxberryCost", boxberryMoscowDeliveryCost);
        modelAndView.addObject("boxberryAvailable", settingsService.getSettingByKey(BOXBERRY_MOSCOW_DELIVERY_AVAILABLE));
        modelAndView.addObject("boxberryFree", SettingsService.isFree(boxberryMoscowDeliveryCost));
        modelAndView.addObject("ukraineDeliveryCost", settingsService.getSettingByKey(DELIVERY_COST_UKRAINE_POST_OFFICE));
        modelAndView.addObject("kazakhstanDeliveryCost", settingsService.getSettingByKey(DELIVERY_COST_KAZAKHSTAN_POST_OFFICE));
        modelAndView.setViewName("shop/info/delivery");
        return modelAndView;
    }
}
