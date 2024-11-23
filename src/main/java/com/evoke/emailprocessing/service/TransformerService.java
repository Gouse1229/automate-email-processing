package com.evoke.emailprocessing.service;

import com.evoke.emailprocessing.model.EmailCategorizer;

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
    String categorizeContent(EmailCategorizer content);
}

