package com.sgm.emailprocessing.service;

import com.sgm.emailprocessing.model.Email;
import com.sgm.emailprocessing.repository.EmailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public void saveEmail(Email email) {
        emailRepository.save(email);
    }

    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    public Email getEmailByMessageId(String messageId) {
        return emailRepository.findByMessageId(messageId);
    }
}
