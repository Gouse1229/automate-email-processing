package com.sgm.emailprocessing.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;

    private String subject;

    private String content;

    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "message_id", unique = true)
    private String messageId;

    private String category;

    //TODO: added in future
//    private List<String> recipients;
//    private String replyTo;
//    private List<String> cc;
//    private List<String> bcc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Email{" +
                "sender='" + sender + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", date='" + createdAt + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
