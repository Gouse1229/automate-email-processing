package com.sgm.emailprocessing.util;
import org.jsoup.Jsoup;
/**
 * Utility methods for email processing.
 */
public class EmailUtils {

    /**
     * Cleans the email content by removing unnecessary parts.
     *
     * @param plainText the raw email content.
     * @return the cleaned email content.
     */
    public static String cleanEmailContent(String plainText) {
        if (plainText == null || plainText.isEmpty()) return "";

        // Remove email signatures and closures
        plainText = plainText.replaceAll("(?i)(\\n--\\n|\\nRegards.*|\\nSincerely.*|\\nThank you.*|\\nBest.*|\\nThanks.*)", "");

        // Remove greetings
        plainText = plainText.replaceAll("(?i)(^Dear .*?,|^Hi .*?,|^Hello,|To whom it may concern,)", "");

        // Remove casual greetings like "Good morning", "Good day", and similar phrases
        plainText = plainText.replaceAll("(?i)(Good morning|Good day|Good evening|Hope you're doing good|Hope you are doing well|Hope this email finds you well)([^a-zA-Z0-9\\s.,!?]+)", "");


        // Remove URLs
        plainText = plainText.replaceAll("https?://\\S+\\s?", "");

        // Remove phone numbers
        plainText = plainText.replaceAll("\\+?\\d[\\d\\s().-]{7,}", "NUMBER");

        // Remove email addresses
        plainText = plainText.replaceAll("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b", "");

        // Remove quoted replies and forwarded messages
        plainText = plainText.replaceAll("(?i)On .*? wrote:.*|(?i)Forwarded message:.*", "");

        // Remove special characters except basic punctuation
        plainText = plainText.replaceAll("[^\\w\\s.,!?]", "");

        // Normalize multiple spaces to a single space
        plainText = plainText.replaceAll("\\s{2,}", " ").trim();

        return plainText.toLowerCase();
    }
}

