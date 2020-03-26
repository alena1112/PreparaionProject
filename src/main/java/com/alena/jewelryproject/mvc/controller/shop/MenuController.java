package com.alena.jewelryproject.mvc.controller.shop;

import com.alena.jewelryproject.mvc.controller.base.ImageHelper;
import com.alena.jewelryproject.mvc.model.Jewelry;
import com.alena.jewelryproject.mvc.model.enums.JewelryType;
import com.alena.jewelryproject.mvc.service.JewelryService;
import com.alena.jewelryproject.mvc.service.OrderService;
import com.alena.jewelryproject.mvc.service.SettingsService;
import com.alena.jewelryproject.spring.ShoppingCart;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.alena.jewelryproject.mvc.controller.base.ControllerHelper.getContextPath;
import static com.alena.jewelryproject.mvc.service.SettingKeys.MAX_NEW_JEWELRY_COUNT;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SettingsService settingsService;
    @Autowired
    private ShoppingCart shoppingCart;

    private static final String MENU_ALL = "all";
    private static final String MENU_NEW = "new";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllJewelries(HttpServletRequest request,
                                        @RequestParam(value = "type") String type) {
        ModelAndView modelAndView = new ModelAndView();
        List<Jewelry> allJewelries = getJewelries(type);
        List<List<Jewelry>> list = new ArrayList<>();
        for (int i = 0; i < allJewelries.size(); i += 4) {
            int end = allJewelries.size() >= i + 4 ? i + 4 : allJewelries.size();
            list.add(allJewelries.subList(i, end));
        }
        modelAndView.addObject("jewelryList", list);
        modelAndView.addObject("imageHelper", new ImageHelper(getContextPath(request)));
        modelAndView.setViewName("shop/menu");
        return modelAndView;
    }

    private List<Jewelry> getJewelries(String menuType) {
        if (StringUtils.isBlank(menuType) || MENU_ALL.equals(menuType)) {
            return jewelryService.getAllUnhiddenJewelries();
        } else if (MENU_NEW.equals(menuType)) {
            return jewelryService.getNewUnhiddenJewelries(new Date(),
                    Integer.parseInt(settingsService.getSettingByKey(MAX_NEW_JEWELRY_COUNT, "3")));
        } else {
            JewelryType jewelryType = JewelryType.fromId(menuType);
            if (jewelryType != null) {
                return jewelryService.getUnhiddenJewelries(jewelryType);
            }
        }
        return new ArrayList<>();
    }
}
