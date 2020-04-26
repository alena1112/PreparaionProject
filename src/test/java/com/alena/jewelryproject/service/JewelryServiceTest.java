package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.JewelryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class JewelryServiceTest {
    @Autowired
    private JewelryService jewelryService;
    @MockBean
    private JewelryRepository jewelryRepository;

    @TestConfiguration
    static class JewelryServiceTestContextConfiguration {
        @Bean
        public JewelryService jewelryService() {
            return new JewelryService();
        }
    }

    @Test
    public void getPagesTest() {
        int countInPage = 16;

        //new jewelries
        assertEquals(Collections.singletonList("1"),
                jewelryService.getPages(countInPage, 1, countInPage));

        assertEquals(Arrays.asList("1", "2", "3", "...", "7"),
                jewelryService.getPages(100, 0, countInPage));

        assertEquals(Arrays.asList("1", "2", "3", "...", "7"),
                jewelryService.getPages(100, -1, countInPage));

        assertEquals(Arrays.asList("1", "2", "3", "...", "7"),
                jewelryService.getPages(100, 1, countInPage));

        assertEquals(Arrays.asList("1", "2", "3", "...", "7"),
                jewelryService.getPages(100, 2, countInPage));

        assertEquals(Arrays.asList( "2", "3", "4", "...", "7"),
                jewelryService.getPages(100, 3, countInPage));

        assertEquals(Arrays.asList("1", "...", "5", "6", "7"),
                jewelryService.getPages(100, 5, countInPage));

        assertEquals(Arrays.asList("1", "...", "5", "6", "7"),
                jewelryService.getPages(100, 7, countInPage));

        assertEquals(Arrays.asList("1", "...", "5", "6", "7"),
                jewelryService.getPages(100, 8, countInPage));

        assertEquals(Collections.singletonList("1"),
                jewelryService.getPages(0, 1, countInPage));
        assertEquals(Collections.singletonList("1"),
                jewelryService.getPages(0, 5, countInPage));

        assertEquals(Arrays.asList("1", "2"),
                jewelryService.getPages(32, 1, countInPage));

        assertEquals(Arrays.asList("1", "2", "3"),
                jewelryService.getPages(48, 2, countInPage));

        assertEquals(Arrays.asList("1", "2", "3", "4"),
                jewelryService.getPages(63, 2, countInPage));

        assertEquals(Arrays.asList("1", "2", "3", "4", "5"),
                jewelryService.getPages(80, 4, countInPage));

        assertEquals(Arrays.asList("1", "2", "3", "...", "6"),
                jewelryService.getPages(95, 1, countInPage));
    }
}
