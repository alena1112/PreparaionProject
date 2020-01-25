package com.alena.preparationproject.mvc.controller.admin;

import com.alena.preparationproject.mvc.model.PromotionalCode;
import com.alena.preparationproject.mvc.model.enums.PromoCodeType;
import com.alena.preparationproject.mvc.service.PromoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@SessionAttributes(value = "promocode")
@RequestMapping("/admin/promocode")
public class PromocodeAdminController {
    @Autowired
    private PromoCodeService promoCodeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getAllPromocodes() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("promocodeList", promoCodeService.getAllPromocodes());
        modelAndView.setViewName("admin/promocode_list");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editPromocode(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        PromotionalCode promotionalCode = id != null ? promoCodeService.getPromotionalCodeById(id) : new PromotionalCode();
        modelAndView.addObject("promocode", promotionalCode);
        modelAndView.addObject("promocodeTypes", PromoCodeType.values());
        modelAndView.setViewName("admin/promocode_edit");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveJewelry(@RequestParam(value = "id") Long id,
                                    @ModelAttribute("promocode") PromotionalCode promotionalCode) {
        if (id == null) {
            promoCodeService.save(promotionalCode);
        } else {
            promoCodeService.update(promotionalCode);
        }
        return new ModelAndView("redirect:/admin/promocode/list", new HashMap<>());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody String deleteJewelry(@RequestParam(value = "id") Long id) {
        promoCodeService.delete(id);
        return "ok";
    }
}
