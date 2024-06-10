package com.javalearnings.securitydemo.services.impl;

import com.javalearnings.securitydemo.model.common.ResponseBoolean;
import com.javalearnings.securitydemo.model.email.EmailForm;
import com.javalearnings.securitydemo.services.EmailService;
import com.javalearnings.securitydemo.utils.EmailNotificationManager;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailNotificationManager emailNotificationManager;

    @Override
    public ResponseEntity<ResponseBoolean> sendEmail(EmailForm emailForm) {
        try {
            emailNotificationManager.sendHtmlMail(emailForm.getTo(), emailForm.getCc(), emailForm.getSubject(), emailForm.getBody());
            log.info("EmailCommonUtils : sendEmail : Email Sent");
        } catch (MessagingException messagingException) {
            log.error("EmailCommonUtils : sendEmail : messageException :", messagingException);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            log.error("EmailCommonUtils : sendEmail : unsupportedEncodingException :", unsupportedEncodingException);
        } catch (Exception exception) {
            log.error("EmailCommonUtils : sendEmail : exception :", exception);
        }
        return new ResponseEntity<>(new ResponseBoolean(Boolean.TRUE), HttpStatus.OK);
    }

}
