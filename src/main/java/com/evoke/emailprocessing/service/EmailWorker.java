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

    /**
     * Constructor for EmailWorker.
     *
     * @param transformerService the TransformerService for categorizing email content.
     */
    public EmailWorker(TransformerService transformerService) {
        this.transformerService = transformerService;
    }

    /**
     * Consumes emails from RabbitMQ and processes them.
     *
     * @param email the email to process.
     */
    @RabbitListener(queues = "email.queue")
    public void processEmail(Email email) {
        String cleanedContent = EmailUtils.cleanEmailContent(email.getContent());
        String category = transformerService.categorizeContent(cleanedContent);

        System.out.println("Processed Email: " + email);
        System.out.println("Category: " + category);
        // Save category to database or log
    }
}
