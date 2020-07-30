package com.alena.jewelryproject.controller.admin;

import com.alena.jewelryproject.controller.base.ImageHelper;
import com.alena.jewelryproject.model.Image;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.enums.JewelryType;
import com.alena.jewelryproject.service.ImageService;
import com.alena.jewelryproject.service.ImportExportJewelriesService;
import com.alena.jewelryproject.service.JewelryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.alena.jewelryproject.controller.base.ControllerHelper.getContextPath;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@SessionAttributes(value = "jewelry")
@RequestMapping("/admin/jewelry")
public class JewelryAdminController {
    private static final Logger log = LoggerFactory.getLogger(JewelryAdminController.class);
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImportExportJewelriesService importExportJewelriesService;

    @GetMapping("/list")
    public ModelAndView getAllJewelries(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jewelryList", jewelryService.getAllJewelries());
        modelAndView.addObject("imageHelper", new ImageHelper(getContextPath(request)));
        modelAndView.setViewName("admin/jewelry_list");
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveJewelry(@ModelAttribute("jewelry") Jewelry jewelry) {
        jewelryService.save(jewelry);
        return new ModelAndView("redirect:/admin/jewelry/list", new HashMap<>());
    }

    @GetMapping("/edit")
    public ModelAndView editJewelry(HttpServletRequest request,
                                    @RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Jewelry jewelry = id != null ? jewelryService.getJewelry(id) : new Jewelry();
        modelAndView.addObject("jewelry", jewelry);
        modelAndView.addObject("jewelryTypes", JewelryType.values());
        modelAndView.addObject("imageHelper", new ImageHelper(jewelry, getContextPath(request)));
        modelAndView.setViewName("admin/jewelry_edit");
        return modelAndView;
    }

    @DeleteMapping("/delete")
    public @ResponseBody
    String deleteJewelry(@RequestParam(value = "id") Long id) {
        jewelryService.deleteJewelry(id);
        return "ok";
    }

    @PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody
    String uploadImage(HttpServletRequest request,
                       @RequestParam("image") MultipartFile multipartFile,
                       @RequestParam("imageOrder") Integer imageOrder,
                       @ModelAttribute("jewelry") Jewelry jewelry) throws IOException {
        Image image = imageService.uploadImage(multipartFile.getOriginalFilename(),
                multipartFile.getBytes(),
                imageOrder,
                jewelry);
        if (image != null) {
            if (jewelry.getImages() == null) {
                jewelry.setImages(new ArrayList<>(4));
            }
            List<Image> images = jewelry.getImages();
            if (images.size() > imageOrder && images.get(imageOrder) != null) {
                imageService.delete(images.get(imageOrder));
                images.set(imageOrder, image);
            } else {
                images.add(image);
            }
            return ImageHelper.getImageFullPath(image.getName(), getContextPath(request));
        } else {
            return "";
        }
    }

    @DeleteMapping(value = "/deleteImage")
    public @ResponseBody
    String deleteImage(@RequestParam("position") Integer i,
                       @ModelAttribute("jewelry") Jewelry jewelry) {
        Image image = jewelry.getImage(i);
        if (image != null) {
            imageService.delete(image);
            jewelry.getImages().remove(image);
        }
        return "ok";
    }

    @PutMapping(value = "/import", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> importJewelries(HttpEntity<String> httpEntity) {
        try {
            String json = httpEntity.getBody();
            if (StringUtils.isNotBlank(json) && importExportJewelriesService.importJewelries(json)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            log.error("Error while importing", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/export", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> exportJewelries() {
        try {
            String json = importExportJewelriesService.exportJewelries();
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            log.error("Error while exporting", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/changePrice", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changePrice(HttpEntity<String> httpEntity) {
        try {
            if (jewelryService.changePrice(httpEntity.getBody())) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
