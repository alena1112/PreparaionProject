package com.alena.jewelryproject.controller.admin;

import com.alena.jewelryproject.model.Settings;
import com.alena.jewelryproject.service.OrderService;
import com.alena.jewelryproject.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@SessionAttributes(value = "setting")
@RequestMapping("/admin/settings")
public class SettingsAdminController {
    @Autowired
    private SettingsService settingsService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getAllSettings() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("settingsList", settingsService.getAllSettings());
        modelAndView.setViewName("admin/settings_list");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView getSetting(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Settings setting = id != null ? settingsService.getSetting(id) : new Settings();
        modelAndView.addObject("setting", setting);
        modelAndView.setViewName("admin/settings_edit");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView createSetting(@ModelAttribute("setting") Settings settings) {
        if (settings.getId() == null) {
            settingsService.save(settings);
        } else {
            settingsService.update(settings);
        }
        return new ModelAndView("redirect:/admin/settings/list", new HashMap<>());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody
    String deleteEmailMessage(@RequestParam(value = "id") Long id) {
        settingsService.delete(id);
        return "ok";
    }
}
