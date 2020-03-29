package com.alena.jewelryproject.mvc.controller.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RobotTxtController {
    private static final Logger log = LoggerFactory.getLogger(RobotTxtController.class);

    @RequestMapping(value = "/robots.txt", produces = {"text/plain"}, method = RequestMethod.GET)
    @ResponseBody
    public String getRobotsTxt() {
        return "User-agent: *\n" +
                "Disallow: /admin/\n" +
                "Disallow: /info\n" +
                "Disallow: /addInOrder\n" +
                "Disallow: /createOrder\n" +
                "Disallow: /deleteItem\n" +
                "Disallow: /checkPromoCode\n" +
                "Disallow: /checkDelivery\n" +
                "Sitemap: https://www.gracefuljewelry.ru/shop/sitemap";
    }
}
