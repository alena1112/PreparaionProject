package com.alena.jewelryproject.controller.api;

import com.alena.jewelryproject.controller.base.ImageHelper;
import com.alena.jewelryproject.controller.dto.JewelryDto;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private JewelryService jewelryService;

    @GetMapping(value = "/jewelry/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JewelryDto> getJewelry() {
        List<Jewelry> jewelries = jewelryService.getAllJewelries();
        return jewelries.stream()
                .map(j -> new JewelryDto(j.getName(), j.getDescription(),
                        ImageHelper.getImageFullPath(j.getImage(0).getName(), "")))
                .collect(Collectors.toList());
    }
}
