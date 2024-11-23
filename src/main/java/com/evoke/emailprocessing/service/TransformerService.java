package com.evoke.emailprocessing.service;

/**
 * Service for categorizing email content using Transformers.
 */
public interface TransformerService {

    /**
     * Categorizes the given email content.
     *
     * @param content the email content to categorize.
     * @return the category of the email.
     */
    String categorizeContent(String content);
}

