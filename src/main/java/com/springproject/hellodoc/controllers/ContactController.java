package com.springproject.hellodoc.controllers;

import com.springproject.hellodoc.models.ContactMessage;
import com.springproject.hellodoc.repositories.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contactMessage", new ContactMessage());
        return "contact";
    }

    @PostMapping("/submit-contact")
    public String submitContact(ContactMessage contactMessage, RedirectAttributes redirectAttributes) {
        contactMessageRepository.save(contactMessage);
        redirectAttributes.addFlashAttribute("message", "Your message has been sent successfully.");
        return "redirect:/contact";
    }
}
