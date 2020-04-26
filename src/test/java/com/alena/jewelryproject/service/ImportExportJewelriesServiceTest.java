package com.alena.jewelryproject.service;

import com.alena.jewelryproject.model.Image;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.enums.JewelryType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ImportExportJewelriesServiceTest {
    @Autowired
    private ImportExportJewelriesService importExportJewelriesService;
    @MockBean
    private JewelryService jewelryService;

    @TestConfiguration
    static class ImportExportJewelriesServiceTestContextConfiguration {
        @Bean
        public ImportExportJewelriesService importExportJewelriesService() {
            return new ImportExportJewelriesService();
        }
    }

    @Test
    public void impostExportJewelriesTest() {
        List<Jewelry> jewelries = generateTestData();
        given(jewelryService.getAllJewelries()).willReturn(jewelries);

        String json = importExportJewelriesService.exportJewelries();
        assertTrue(importExportJewelriesService.importJewelries(json));

        ArgumentCaptor<List<Jewelry>> argument = ArgumentCaptor.forClass(ArrayList.class);
        verify(jewelryService).saveAll(argument.capture());

        List<Jewelry> result = argument.getValue();
        assertEquals(2, result.size());
        assertFalse(result.get(0).getName().isEmpty());
        assertEquals(2, result.get(0).getImages().size());
        assertNotNull(result.get(1).getPrice());
        assertEquals(2, result.get(1).getImages().size());
    }

    private List<Jewelry> generateTestData() {
        Jewelry bracelet = new Jewelry();
        bracelet.setName("bracelet");
        bracelet.setPrice(1200.0);
        bracelet.setType(JewelryType.BRACELET);
        bracelet.setImages(new ArrayList<Image>() {{
            Image image0 = new Image();
            image0.setName("Image bracelet");
            image0.setIndex(0);
            image0.setJewelry(bracelet);
            add(image0);
            Image image1 = new Image();
            image1.setName("Image bracelet");
            image1.setIndex(1);
            image1.setJewelry(bracelet);
            add(image1);
        }});
        Jewelry earrings = new Jewelry();
        earrings.setName("earrings");
        earrings.setPrice(1300.0);
        earrings.setType(JewelryType.EARRINGS);
        earrings.setImages(new ArrayList<Image>() {{
            Image image0 = new Image();
            image0.setName("Image earrings");
            image0.setIndex(0);
            image0.setJewelry(earrings);
            add(image0);
            Image image1 = new Image();
            image1.setName("Image earrings");
            image1.setIndex(1);
            image1.setJewelry(earrings);
            add(image1);
        }});
        return Arrays.asList(bracelet, earrings);
    }
}
