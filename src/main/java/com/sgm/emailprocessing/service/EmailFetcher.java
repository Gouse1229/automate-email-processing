package com.sgm.emailprocessing.service;

import com.sgm.emailprocessing.model.Email;

import java.util.List;

/**
 * Interface for fetching emails from an email server.
 */
public interface EmailFetcher {

    /**
     * Fetches new emails from the email server.
     *
     * @return a list of new emails.
     */
    List<Email> fetchEmails();

    List<Email> fetchEmailsNlp();
}
