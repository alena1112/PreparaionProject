package com.alena.preparationproject.mvc.controller.admin;

import com.alena.preparationproject.mvc.model.EmailMessage;
import com.alena.preparationproject.mvc.model.enums.EmailMessageToType;
import com.alena.preparationproject.mvc.service.EmailMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@SessionAttributes(value = "email")
@RequestMapping("/admin/emails")
public class EmailAdminController {
    @Autowired
    private EmailMessagesService emailMessagesService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getAllEmailMessages() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("emailsList", emailMessagesService.getAllEmailMessages());
        modelAndView.setViewName("admin/email_messages_list");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editEmailMessage(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        EmailMessage emailMessage = id != null ? emailMessagesService.getEmailMessage(id) : new EmailMessage();
        modelAndView.addObject("emailMsg", emailMessage);
        modelAndView.addObject("toTypes", EmailMessageToType.values());
        modelAndView.setViewName("admin/email_message_edit");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveEmailMessage(@RequestParam(value = "id") Long id,
                                         @ModelAttribute("emailMsg") EmailMessage emailMessage) {
        if (id == null) {
            emailMessagesService.save(emailMessage);
        } else {
            emailMessagesService.update(emailMessage);
        }
        return new ModelAndView("redirect:/admin/emails/list", new HashMap<>());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody String deleteEmailMessage(@RequestParam(value = "id") Long id) {
        emailMessagesService.deleteEmailMessage(id);
        return "ok";
    }
}