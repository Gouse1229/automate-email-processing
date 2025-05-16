package com.sgm.emailprocessing.controller;

import com.sgm.emailprocessing.model.Email;
import com.sgm.emailprocessing.service.EmailFetcher;
import com.sgm.emailprocessing.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmailController {

    private final EmailService emailService;

    private final EmailFetcher fetcher;

    public EmailController(EmailService emailService, EmailFetcher fetcher) {
        this.emailService = emailService;
        this.fetcher = fetcher;
    }

    @GetMapping("/emails")
    public String showEmails(Model model) {
        model.addAttribute("emails", emailService.getAllEmails());
        return "emails";  // This will return emails.html template
    }

    @GetMapping("/process")
    public String processEmails() {
        System.out.println("Inside-Process: Fetching Emails");
        List<Email> emails =fetcher.fetchEmails();
        System.out.println("Fetched "+emails.size()+" emails");
        return "index";
    }

    @GetMapping("/processOpenNlp")
    public String processEmailsNlp() {
        System.out.println("Inside-Process: Fetching Emails");
        List<Email> emails =fetcher.fetchEmailsNlp();
        System.out.println("Fetched "+emails.size()+" emails");
        return "index";
    }
}
