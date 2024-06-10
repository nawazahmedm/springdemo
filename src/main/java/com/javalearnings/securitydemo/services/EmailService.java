package com.javalearnings.securitydemo.services;

import com.javalearnings.securitydemo.model.common.ResponseBoolean;
import com.javalearnings.securitydemo.model.email.EmailForm;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<ResponseBoolean> sendEmail(EmailForm emailForm);

}
