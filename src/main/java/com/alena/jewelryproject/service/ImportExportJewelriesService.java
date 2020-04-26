package com.alena.jewelryproject.service;

import com.alena.jewelryproject.model.Jewelry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportExportJewelriesService {
    @Autowired
    private JewelryService jewelryService;
    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public boolean importJewelries(String json) {
        List<Jewelry> jewelries = gson.fromJson(json, Jewelries.class).getJewelries();
        if (CollectionUtils.isNotEmpty(jewelries)) {
            jewelries.forEach(jewelry -> {
                if (jewelry.getImages() != null) {
                    jewelry.getImages().forEach(image -> image.setJewelry(jewelry));
                }
            });
            jewelryService.saveAll(jewelries);
            return true;
        }
        return false;
    }

    public String exportJewelries() {
        List<Jewelry> jewelries = jewelryService.getAllJewelries();
        String json = gson.toJson(new Jewelries(jewelries));
        return json;
    }

    @AllArgsConstructor
    @Data
    private static class Jewelries {
        @Expose
        private List<Jewelry> jewelries;
    }
}
