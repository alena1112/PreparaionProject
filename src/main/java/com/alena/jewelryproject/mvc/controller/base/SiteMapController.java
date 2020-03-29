package com.alena.jewelryproject.mvc.controller.base;

import com.alena.jewelryproject.mvc.service.sitemap.SiteMapService;
import com.alena.jewelryproject.mvc.service.sitemap.Urlset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sitemap")
public class SiteMapController {
    @Autowired
    private SiteMapService siteMapService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Urlset getSiteMap() {
        return siteMapService.generateUrlset();
    }
}
