package com.evoke.emailprocessing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller to expose email processing API endpoints.
 */
@RestController
public class EmailController {

    @GetMapping("/health")
    public String processEmails() {
        // Trigger the scheduled job manually if needed
        return "running";
    }
}

