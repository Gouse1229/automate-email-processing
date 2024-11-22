package com.evoke.emailprocessing.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ.
 */
@Configuration
public class RabbitMQConfig {

    /**
     * The name of the email queue.
     */
    public static final String EMAIL_QUEUE = "email.queue";

    /**
     * Defines a durable queue for processing emails.
     *
     * @return the configured queue.
     */
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true); // Durable queue
    }
}

