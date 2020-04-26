package com.alena.jewelryproject.controller.shop;

import com.alena.jewelryproject.controller.base.ImageHelper;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.enums.JewelryType;
import com.alena.jewelryproject.service.JewelryService;
import com.alena.jewelryproject.service.sitemap.NeedInSiteMap;
import com.alena.jewelryproject.spring.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.alena.jewelryproject.controller.base.ControllerHelper.getContextPath;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private ShoppingCart shoppingCart;
    @Value("${menu.page.count}")
    private Integer countInPage;

    @NeedInSiteMap(paths = {"?type=all", "?type=only_new", "?type=bracelet", "?type=earrings", "?type=necklace"})
    @GetMapping
    public ModelAndView getAllJewelries(HttpServletRequest request,
                                        @RequestParam(value = "type", required = false) MenuType type,
                                        @RequestParam(value = "page", required = false) Integer page) {
        List<String> pages = jewelryService.getPages(getAllUnhiddenJewelriesCount(type), page, countInPage);
        int currentPage = jewelryService.getCorrectPage(page, Integer.parseInt(pages.get(pages.size() - 1)));
        List<Jewelry> allJewelries = getJewelries(type, currentPage);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jewelryList", generateListForPage(allJewelries));
        modelAndView.addObject("imageHelper", new ImageHelper(getContextPath(request)));
        modelAndView.addObject("type", type);
        modelAndView.addObject("pages", pages);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.setViewName("shop/menu");
        return modelAndView;
    }

    private List<Jewelry> getJewelries(MenuType menuType, int page) {
        if (menuType == null || menuType == MenuType.all) {
            return jewelryService.getAllUnhiddenJewelries(page - 1, countInPage);
        } else if (menuType == MenuType.only_new) {
            return jewelryService.getNewUnhiddenJewelries(countInPage);
        } else {
            JewelryType jewelryType = JewelryType.fromId(menuType.name());
            if (jewelryType != null) {
                return jewelryService.getUnhiddenJewelries(jewelryType, page - 1, countInPage);
            }
        }
        return new ArrayList<>();
    }

    public int getAllUnhiddenJewelriesCount(MenuType menuType) {
        switch (menuType) {
            case only_new:
                return countInPage;
            case bracelet:
            case earrings:
            case necklace:
                return jewelryService.getAllJewelriesCount(JewelryType.fromId(menuType.name()));
            case all:
            default:
                return jewelryService.getAllJewelriesCount();
        }
    }

    public List<List<Jewelry>> generateListForPage(List<Jewelry> allJewelries) {
        List<List<Jewelry>> list = new ArrayList<>();
        for (int i = 0; i < allJewelries.size(); i += 4) {
            int end = Math.min(allJewelries.size(), i + 4);
            list.add(allJewelries.subList(i, end));
        }
        return list;
    }
}
