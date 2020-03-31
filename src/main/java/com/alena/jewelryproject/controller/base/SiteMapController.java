package com.alena.jewelryproject.controller.base;

import com.alena.jewelryproject.service.sitemap.SiteMapService;
import com.alena.jewelryproject.service.sitemap.Urlset;
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
    @ResponseBody
    public Urlset getSiteMap() {
        return siteMapService.generateUrlset();
    }
}
