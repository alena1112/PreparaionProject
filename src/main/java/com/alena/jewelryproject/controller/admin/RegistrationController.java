package com.alena.jewelryproject.controller.admin;

import com.alena.jewelryproject.model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping()
public class RegistrationController {
    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @GetMapping("/registration")
    public String registration(Model model, @RequestParam(value = "msg", required = false) String errorMsg) {
        log.info("Need to login to admin page");
        model.addAttribute("userForm", new User());
        if (StringUtils.isNotBlank(errorMsg)) {
            model.addAttribute("errorMsg", errorMsg);
        }
        return "admin/registration";
    }
}
