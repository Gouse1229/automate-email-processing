package com.evoke.emailprocessing.util;
import org.jsoup.Jsoup;
/**
 * Utility methods for email processing.
 */
public class EmailUtils {

    /**
     * Cleans the email content by removing unnecessary parts.
     *
     * @param content the raw email content.
     * @return the cleaned email content.
     */
    public static String cleanEmailContent(String content) {
        if (content == null || content.isEmpty()) return "";

        String plainText = Jsoup.parse(content).text();
        plainText = plainText.replaceAll("(?i)(\\n--\\n|\\nRegards.*|\\nSincerely.*|\\nThank you.*|\\nBest.*)", "");
        plainText = plainText.replaceAll("(?i)(^Dear .*?,|^Hi .*?,|^Hello,|To whom it may concern,)", "");
        plainText = plainText.replaceAll("https?://\\S+\\s?", "");
        plainText = plainText.replaceAll("\\+?\\d[\\d\\s().-]{7,}", "");
        plainText = plainText.replaceAll("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b", "");
        plainText = plainText.replaceAll("(?i)On .*? wrote:.*|(?i)Forwarded message:.*", "");
        plainText = plainText.replaceAll("[^\\w\\s.,!?]", "");
        plainText = plainText.replaceAll("\\s{2,}", " ").trim();

        return plainText.toLowerCase();
    }
}

