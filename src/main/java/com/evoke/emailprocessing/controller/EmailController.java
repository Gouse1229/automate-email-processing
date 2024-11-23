package com.evoke.emailprocessing.controller;

import com.evoke.emailprocessing.model.Email;
import com.evoke.emailprocessing.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/emails")
    public String showEmails(Model model) {
        model.addAttribute("emails", emailService.getAllEmails());
        return "emails";  // This will return emails.html template
    }

    @PostMapping("/emails")
    public String categorizeEmail(Email email) {
        emailService.saveEmail(email);  // Save email to DB
        return "redirect:/emails";  // Redirect to the email list
    }
}
