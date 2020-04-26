package com.alena.jewelryproject.service.sitemap;

import com.alena.jewelryproject.service.JewelryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void generateUrlsetTest() {
        Urlset urlset = siteMapService.generateUrlset();
        assertTrue(urlset.getUrl().size() >= 7);
    }
}
