package com.alena.jewelryproject.controller.base;

import com.alena.jewelryproject.service.ImageFileService;
import com.alena.jewelryproject.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageFileService imageFileService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@RequestParam(value = "name") String name) {
        HttpHeaders headers = new HttpHeaders();
        byte[] media = imageFileService.loadImage(name);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
}
