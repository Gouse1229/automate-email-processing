package com.evoke.emailprocessing.scheduler;

import com.evoke.emailprocessing.service.EmailFetcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * EmailScheduler to periodically fetch emails and send them to the RabbitMQ queue.
 */
@Component
public class EmailScheduler {

    private final EmailFetcher emailFetcher;

    /**
     * Constructor for EmailScheduler.
     *
     * @param emailFetcher the EmailFetcher service for fetching emails.
     */
    public EmailScheduler(EmailFetcher emailFetcher) {
        this.emailFetcher = emailFetcher;
    }

    /**
     * Scheduled task to fetch emails at fixed intervals.
     * This method will run every 5 minutes.
     */
    @Scheduled(cron = "0 0/5 * * * ?")  // Cron expression to run every 5 minutes
    public void fetchEmailsAndSendToQueue() {
        // Fetch emails from IMAP and send to RabbitMQ queue
//        emailFetcher.fetchEmails();

        System.out.println("Emails fetched and sent to queue.");
    }
}
