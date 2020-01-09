package com.alena.preparationproject.mvc.controller.shop;

import com.alena.preparationproject.mvc.controller.base.ImageHelper;
import com.alena.preparationproject.mvc.model.Jewelry;
import com.alena.preparationproject.mvc.model.Order;
import com.alena.preparationproject.mvc.model.enums.JewelryType;
import com.alena.preparationproject.mvc.service.JewelryService;
import com.alena.preparationproject.mvc.service.OrderService;
import com.alena.preparationproject.mvc.service.SettingsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.alena.preparationproject.mvc.controller.base.ControllerHelper.getContextPath;

@Controller
@SessionAttributes(value = "order")
@RequestMapping("/start")
public class StartController {
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SettingsService settingsService;

    private static final String MENU_ALL = "all";
    private static final String MENU_NEW = "new";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllJewelries(HttpServletRequest request,
                                        @RequestParam(value = "menu") String menu) {
        ModelAndView modelAndView = new ModelAndView();
        List<Jewelry> allJewelries = getJewelries(menu);
        List<List<Jewelry>> list = new ArrayList<>();
        for (int i = 0; i < allJewelries.size(); i += 4) {
            int end = allJewelries.size() >= i + 4 ? i + 4 : allJewelries.size();
            list.add(allJewelries.subList(i, end));
        }
        modelAndView.addObject("jewelryList", list);
        modelAndView.addObject("imageHelper", new ImageHelper(getContextPath(request)));
        modelAndView.setViewName("shop/start");
        return modelAndView;
    }

    private List<Jewelry> getJewelries(String menu) {
        if (StringUtils.isBlank(menu) || MENU_ALL.equals(menu)) {
            return jewelryService.getAllUnhiddenJewelries();
        } else if (MENU_NEW.equals(menu)) {
            return jewelryService.getNewUnhiddenJewelries(new Date(),
                    Integer.parseInt(settingsService.getSettingsByKey("maxNewJewelryCount", "3")));
        } else {
            JewelryType jewelryType = JewelryType.fromId(menu);
            if (jewelryType != null) {
                return jewelryService.getUnhiddenJewelries(jewelryType);
            }
        }
        return new ArrayList<>();
    }

    @ModelAttribute("order")
    public Order createOrder() {
        return orderService.createDefaultOrder();
    }
}
