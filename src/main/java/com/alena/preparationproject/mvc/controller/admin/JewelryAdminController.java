package com.alena.preparationproject.mvc.controller.admin;

import com.alena.preparationproject.mvc.controller.base.ImageHelper;
import com.alena.preparationproject.mvc.model.Image;
import com.alena.preparationproject.mvc.model.Jewelry;
import com.alena.preparationproject.mvc.model.enums.JewelryType;
import com.alena.preparationproject.mvc.service.ImageService;
import com.alena.preparationproject.mvc.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.alena.preparationproject.mvc.controller.base.ControllerHelper.getContextPath;

@Controller
@SessionAttributes(value = "jewelry")
@RequestMapping("/admin/jewelry")
public class JewelryAdminController {
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private ImageService imageService;

    @ModelAttribute("jewelry")
    public Jewelry createJewelry() {
        return new Jewelry();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getAllJewelries(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jewelryList", jewelryService.getAllJewelries());
        modelAndView.addObject("imageHelper", new ImageHelper(getContextPath(request)));
        modelAndView.setViewName("admin/jewelry_list");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveJewelry(@RequestParam(value = "id") Long id,
                                    @ModelAttribute("jewelry") Jewelry jewelry) {
        if (id == null) {
            jewelryService.save(jewelry);
        } else {
            jewelryService.update(jewelry);
        }
        return new ModelAndView("redirect:/admin/jewelry/list", new HashMap<>());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
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

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody String deleteJewelry(@RequestParam(value = "id") Long id) {
        jewelryService.deleteJewelry(id);
        return "ok";
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody String uploadImage(HttpServletRequest request,
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

    @RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
    public @ResponseBody String deleteImage(@RequestParam("position") Integer i,
                                            @ModelAttribute("jewelry") Jewelry jewelry) {
        Image image = jewelry.getImage(i);
        if (image != null) {
            imageService.delete(image);
            jewelry.getImages().remove(image);
        }
        return "ok";
    }
}
