package com.javalearnings.securitydemo.controllers.impl;

import com.javalearnings.securitydemo.controllers.EmailController;
import com.javalearnings.securitydemo.exceptions.BusinessException;
import com.javalearnings.securitydemo.exceptions.GenericException;
import com.javalearnings.securitydemo.model.common.ResponseBoolean;
import com.javalearnings.securitydemo.model.email.EmailForm;
import com.javalearnings.securitydemo.services.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class EmailControllerImpl implements EmailController {

    private final EmailService emailService;

    @Override
    public ResponseEntity<ResponseBoolean> sendEmail(Integer userId, EmailForm emailForm) {
        log.debug("EmailControllerImpl : validateLogin : Start: emailForm {}", emailForm);
        try {
            ResponseEntity<ResponseBoolean> responseEntity = emailService.sendEmail(emailForm);
            log.debug("EmailControllerImpl : validateLogin : responseEntity {}", responseEntity);
            return responseEntity;
        } catch (BusinessException businessException) {
            log.error("EmailControllerImpl : validateLogin : BusinessException : ", businessException);
            throw new BusinessException(businessException.getCode(), businessException.getMessage(), businessException.getHttpStatus());
        } catch (Exception exception) {
            log.error("EmailControllerImpl : validateLogin : Exception : ", exception);
            throw new GenericException(exception);
        }
    }

}
