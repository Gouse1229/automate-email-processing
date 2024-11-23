package com.evoke.emailprocessing.service;

import com.evoke.emailprocessing.model.Email;
import com.evoke.emailprocessing.util.EmailUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Worker service for consuming emails from the RabbitMQ queue and categorizing them.
 */
@Service
public class EmailWorker {

    private final TransformerService transformerService;

    private final EmailService emailService;

    /**
     * Constructor for EmailWorker.
     *
     * @param transformerService the TransformerService for categorizing email content.
     */
    public EmailWorker(TransformerService transformerService, EmailService emailService) {
        this.transformerService = transformerService;
        this.emailService= emailService;
    }

    /**
     * Consumes emails from RabbitMQ and processes them.
     *
     * @param email the email to process.
     */
    @RabbitListener(queues = "email.queue",concurrency = "5-10")
    public void processEmail(Email email) {
        String cleanedContent = EmailUtils.cleanEmailContent(email.getContent());
        String category = transformerService.categorizeContent(cleanedContent);
        email.setCategory(category);
        emailService.saveEmail(email);

        System.out.println("Processed Email: " + email);
        System.out.println("Category: " + category);

    }
}
