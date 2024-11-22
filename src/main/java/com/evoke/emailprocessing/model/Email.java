package com.evoke.emailprocessing.model;

import java.io.Serializable;
import java.util.List;

public class Email implements Serializable {
    private String sender;

    private String subject;

    private String content;

    private String date;

    private String messageId;

    //TODO: added in future
//    private List<String> recipients;
//    private String replyTo;
//    private List<String> cc;
//    private List<String> bcc;


    /**
     * Constructor for the Email class.
     *
     * @param sender    the sender's email address.
     * @param subject   the email subject.
     * @param content   the email content.
     * @param messageId the unique message ID of the email.
     * @param date      the date the email was received.
     */
    public Email(String sender, String subject, String content, String messageId, String date) {
        this.sender = sender;
        this.subject = subject;
        this.content = content;
        this.messageId = messageId;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "Email{" +
                "sender='" + sender + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
