package com.alena.jewelryproject.service.sitemap;

import com.alena.jewelryproject.service.JewelryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class SiteMapServiceTest {
    @Autowired
    private SiteMapService siteMapService;
    @MockBean
    private JewelryService jewelryService;

    @TestConfiguration
    static class SiteMapServiceTestContextConfiguration {
        @Bean
        public SiteMapService siteMapService() {
            return new SiteMapService();
        }
    }

    //TODO починить sitemap
    @Test
    public void generateUrlsetTest() {
//        Urlset urlset = siteMapService.generateUrlset();
//        assertTrue(urlset.getUrl().size() >= 7);
//        List<Url> urls = urlset.getUrl();
//        assertTrue(urls.stream().anyMatch(url -> url.getLoc().contains("delivery")));
//        assertTrue(urls.stream().anyMatch(url -> url.getLoc().contains("about")));
//        assertTrue(urls.stream().anyMatch(url -> url.getLoc().contains("payment")));
//        assertTrue(urls.stream().anyMatch(url -> url.getLoc().contains("return")));
//        assertTrue(urls.stream().anyMatch(url -> url.getLoc().contains("contacts")));
    }
}
