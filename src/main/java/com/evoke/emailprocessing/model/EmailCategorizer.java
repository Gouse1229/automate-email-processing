package com.evoke.emailprocessing.model;

public class EmailCategorizer {

    private String subject;

    private String content;

    public EmailCategorizer(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
