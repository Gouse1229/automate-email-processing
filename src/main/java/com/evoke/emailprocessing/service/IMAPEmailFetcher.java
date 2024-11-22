package com.evoke.emailprocessing.service;

import com.evoke.emailprocessing.config.RabbitMQConfig;
import com.evoke.emailprocessing.model.Email;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Implementation of EmailFetcher for fetching emails via IMAP.
 */
@Service
public class IMAPEmailFetcher implements EmailFetcher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Constructor for IMAPEmailFetcher.
     *
     * @param rabbitTemplate the RabbitTemplate for publishing messages to RabbitMQ.
     */
    public IMAPEmailFetcher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<Email> fetchEmails() {
        List<Email> emails = new ArrayList<>();
        try {
            String host = "imap.example.com";
            String user = "your-email@example.com";
            String password = "your-password";

            Properties properties = new Properties();
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.ssl.enable", "true");
            Session emailSession = Session.getInstance(properties);

            Store store = emailSession.getStore("imap");
            store.connect(user, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (Message message : messages) {
                MimeMessage mimeMessage = (MimeMessage) message;
                String sender = mimeMessage.getFrom()[0].toString();
                String subject = mimeMessage.getSubject();
                String content = mimeMessage.getContent().toString();
                String messageId = mimeMessage.getMessageID();
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mimeMessage.getSentDate());

                Email email = new Email(sender, subject, content, messageId, date);

                // Publish email to RabbitMQ
                rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, email);

                // Mark email as read
                message.setFlag(Flags.Flag.SEEN, true);

                emails.add(email);
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return emails;
    }
}
