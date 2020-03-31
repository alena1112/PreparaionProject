package com.alena.jewelryproject.controller.admin;

import com.alena.jewelryproject.model.User;
import com.alena.jewelryproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "admin/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") User userForm) {
        UserDetails userDetails = userService.loadUserByUsername(userForm.getUsername());
        if (!userDetails.getPassword().equals(userForm.getPassword())) {
            return "admin/registration";
        }

        return "redirect:/admin/jewelry/list";
    }
}
