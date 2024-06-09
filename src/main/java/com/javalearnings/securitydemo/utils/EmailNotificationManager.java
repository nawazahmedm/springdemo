package com.javalearnings.securitydemo.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.javalearnings.securitydemo.events.FooterEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * The type Email notification
 */
@Component
@RequiredArgsConstructor
public class EmailNotificationManager {

    @Value("${email.maxRetries:3}")
    private int maxRetries;

    @Value("${email.retryInterval:10000}")
    private int retryInterval;

    MimeMessage mimeMessage;
    MimeMessageHelper helper;

    private final JavaMailSender javaMailSender;

    @PostConstruct
    public void setup() throws MessagingException {
        mimeMessage = javaMailSender.createMimeMessage();
    }

    /**
     * Send text mail
     *
     * @param from    From Email Address
     * @param to      To Email Address
     * @param subject the subject
     * @param body    the body
     */
    public void sendTextMail(String from, String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);
    }

    /**
     * Send html mail
     *
     * @param to       To Email Address
     * @param cc       CC Email Address
     * @param subject  Subject of the Email
     * @param htmlText Body
     * @param replyTo ReplyTo
     */
    public void sendHtmlMail(String to, String cc, String subject, String htmlText, String replyTo)
            throws MessagingException, UnsupportedEncodingException {
        helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setTo(to);
        helper.setReplyTo(Objects.requireNonNullElse(replyTo, ""));
        helper.setCc(Objects.requireNonNullElse(cc, ""));
        helper.setSubject(subject);
        helper.setText(htmlText, true);
        mimeMessage.saveChanges();
        javaMailSender.send(mimeMessage);
    }

    /**
     * Send html mail
     *
     * @param to       To Email Address
     * @param cc       CC List of Email Address
     * @param subject  Subject of the Email
     * @param htmlText Body
     * @param replyTo ReplyTo
     */
    public void sendHtmlMail(String to, String[] cc, String subject, String htmlText, String replyTo)
            throws MessagingException, UnsupportedEncodingException {
        helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setTo(to);
        helper.setReplyTo(Objects.requireNonNullElse(replyTo, ""));
        if (cc != null) {
            helper.setCc(cc);
        } else {
            helper.setCc("");
        }
        helper.setSubject(subject);
        helper.setText(htmlText, true);
        mimeMessage.saveChanges();
        javaMailSender.send(mimeMessage);
    }

    public void sendHtmlMailWithRetry(String to, String cc, String subject, String htmlText)
            throws UnsupportedEncodingException {

        for (int retry = 0; retry < maxRetries; retry++) {
            try {
                sendHtmlMail(to, cc, subject, htmlText);
                return; // If successful, exit the loop
            } catch (MessagingException | MailAuthenticationException e) {
                e.printStackTrace(); // Log the exception
                System.out.println("Retry attempt " + (retry + 1) + " in 10 seconds...");
                try {
                    Thread.sleep(retryInterval); // Wait for the retry interval
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Send html mail
     *
     * @param to       To Email Address
     * @param cc       CC Email Address
     * @param subject  Subject of the Email
     * @param htmlText Body
     */
    public void sendHtmlMail(String to, String cc, String subject, String htmlText)
            throws MessagingException, UnsupportedEncodingException {
        helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setTo(to);
        helper.setCc(Objects.requireNonNullElse(cc, ""));
        helper.setSubject(subject);
        helper.setText(htmlText, true);
        mimeMessage.saveChanges();
        javaMailSender.send(mimeMessage);
    }

    public String convertToPdfWithFooterAndSendEmailWithAttachment(String to, String[] cc, String subject, String body, String htmlContent, String replyTo) {
        try {
            byte[] pdfBytes = convertToPdfWithFooter(htmlContent);
            // Send email with PDF attachment
            sendEmailWithAttachment(
                    to,
                    cc,
                    subject,
                    body,
                    pdfBytes,
                    replyTo
            );
            return "Email sent successfully with PDF attachment.";
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            return "Failed to send email.";
        }
    }

    public byte[] convertToPdfWithFooter(String html) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterEventHandler());

        ConverterProperties props = new ConverterProperties();
        Document document = HtmlConverter.convertToDocument(html, pdfDoc, props);
        document.close();
        return baos.toByteArray();
    }

    public String convertToPdfAndSendEmailWithAttachment(String to, String[] cc, String subject, String body, String htmlContent, String replyTo) {
        try {
            // Convert HTML to PDF
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(htmlContent, pdfOutputStream);

            // Send email with PDF attachment
            sendEmailWithAttachment(
                    to,
                    cc,
                    subject,
                    body,
                    pdfOutputStream.toByteArray(),
                    replyTo
            );

            return "Email sent successfully with PDF attachment.";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send email.";
        }
    }

    public void sendEmailWithAttachment(String to, String[] cc, String subject, String body, byte[] pdfData, String replyTo) throws MessagingException {
        helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setTo(to);
        helper.setReplyTo(Objects.requireNonNullElse(replyTo, ""));
        if (cc != null) {
            helper.setCc(cc);
        } else {
            helper.setCc("");
        }
        helper.setSubject(subject);
        helper.setText(body, true);

        ByteArrayDataSource dataSource = new ByteArrayDataSource(pdfData, "application/pdf");
        helper.addAttachment("converted.pdf", dataSource);

        mimeMessage.saveChanges();
        javaMailSender.send(mimeMessage);
    }

}
