package com.evoke.emailprocessing.service;

import com.evoke.emailprocessing.config.RabbitMQConfig;
import com.evoke.emailprocessing.model.Email;
import com.evoke.emailprocessing.util.EmailUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Implementation of EmailFetcher for fetching emails via IMAP.
 */
@Service
public class IMAPEmailFetcher implements EmailFetcher {

    private final RabbitTemplate rabbitTemplate;

    private final EmailService emailService;

    private final TransformerService transformerService;

    /**
     * Constructor for IMAPEmailFetcher.
     *
     * @param rabbitTemplate     the RabbitTemplate for publishing messages to RabbitMQ.
     * @param emailService       the email service
     * @param transformerService
     */
    public IMAPEmailFetcher(RabbitTemplate rabbitTemplate, EmailService emailService, TransformerService transformerService) {
        this.rabbitTemplate = rabbitTemplate;
        this.emailService = emailService;
        this.transformerService = transformerService;
    }
    @Value("${email.username}")
    private String user;
    @Value("${email.password}")
    private String password;

    @Value("${email.host}")
    private String host;


    @Override
    public List<Email> fetchEmails() {
        List<Email> emails = new ArrayList<>();
        try {

            Properties properties = new Properties();
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.ssl.enable", "true");
            properties.put("mail.imap.auth", "true");
            Session emailSession = Session.getInstance(properties);
            emailSession.setDebug(true);

            Store store = emailSession.getStore("imap");
            store.connect(host, user, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (Message message : messages) {
                MimeMessage mimeMessage = (MimeMessage) message;
                String sender = mimeMessage.getFrom()[0].toString();
                String subject = mimeMessage.getSubject();
                String content = getTextFromMessage(mimeMessage);
//                String content = mimeMessage.getContent().toString();
                String messageId = mimeMessage.getMessageID();
                Date sentDate = mimeMessage.getSentDate();

                Email email = new Email();
                email.setSender(sender);
                email.setSubject(subject);
                email.setContent(content);
                email.setMessageId(messageId);
                email.setCategory("Uncategorized");
                email.setCreatedAt(sentDate);
                String cleanedContent = messageId+";"+subject + EmailUtils.cleanEmailContent(content);
                emailService.saveEmail(email);
                // Publish email to RabbitMQ
                rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, cleanedContent);

                // Mark email as read
                message.setFlag(Flags.Flag.SEEN, true);

                emails.add(email);
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return emails;
    }

    @Override
    public List<Email> fetchEmailsNlp() {
        List<Email> emails = new ArrayList<>();
        try {

            Properties properties = new Properties();
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.ssl.enable", "true");
            properties.put("mail.imap.auth", "true");
            Session emailSession = Session.getInstance(properties);
            emailSession.setDebug(true);

            Store store = emailSession.getStore("imap");
            store.connect(host, user, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (Message message : messages) {
                MimeMessage mimeMessage = (MimeMessage) message;
                String sender = mimeMessage.getFrom()[0].toString();
                String subject = mimeMessage.getSubject();
                String content = getTextFromMessage(mimeMessage);
                String messageId = mimeMessage.getMessageID();
                Date sentDate = mimeMessage.getSentDate();

                Email email = new Email();
                email.setSender(sender);
                email.setSubject(subject);
                email.setContent(content);
                email.setMessageId(messageId);
                email.setCategory("Uncategorized");
                email.setCreatedAt(sentDate);
                String cleanedContent = messageId+";"+subject +" "+ EmailUtils.cleanEmailContent(content);

                String category = transformerService.categorizeContentForOpenNlp(content);
                email.setCategory(category);
                emailService.saveEmail(email);
                // Mark email as read
                message.setFlag(Flags.Flag.SEEN, true);

                emails.add(email);
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return emails;
    }

    private static String getTextFromMessage(Message message) throws Exception {
        String content = "";
        if (message.isMimeType("text/plain")) {
            content = (String) message.getContent();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            content = getTextFromMultipart(mimeMultipart);
        }
        return content;
    }

    private static String getTextFromMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            String contentType = bodyPart.getContentType().toLowerCase();

            if (contentType.contains("text/plain")) {
                content.append(bodyPart.getContent().toString());
            }else if (contentType.contains("text/html")){
                String htmlContent = (String) bodyPart.getContent();

                // Convert HTML to plain text using jsoup
                return convertHtmlToPlainText(htmlContent);
            }
        }
        return content.toString();
    }

    private static String convertHtmlToPlainText(String html) {
        Document doc = Jsoup.parse(html);
        return doc.text(); // Extracts plain text without HTML tags
    }
}
