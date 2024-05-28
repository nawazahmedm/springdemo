package com.javalearnings.securitydemo.controllers.impl;

import com.javalearnings.securitydemo.controllers.LoginController;
import com.javalearnings.securitydemo.exceptions.BusinessException;
import com.javalearnings.securitydemo.exceptions.GenericException;
import com.javalearnings.securitydemo.model.login.RequestLoginForm;
import com.javalearnings.securitydemo.model.login.ResponseLoginForm;
import com.javalearnings.securitydemo.services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@Slf4j
@AllArgsConstructor
public class LoginControllerImpl implements LoginController {

    private final LoginService loginService;

    @Override
    public ResponseEntity<ResponseLoginForm> validateLogin(HttpServletRequest httpServletRequest, String clientIPAddress, RequestLoginForm requestLoginForm) {
        log.debug("LoginController : validateLogin : Start: requestLoginForm {}", requestLoginForm);
        try {
            if (StringUtils.isNotBlank(clientIPAddress)) {
                String ipAddress = getIpAddress(clientIPAddress);
                log.debug("ipAddress Info: {}", ipAddress);
            }
            ResponseEntity<ResponseLoginForm> userResponseEntity = loginService.validateLogin(requestLoginForm);
            log.debug("LoginController : validateLogin : userResponseEntity {}", userResponseEntity);
            return userResponseEntity;
        } catch (BusinessException businessException) {
            log.error("LoginController : validateLogin : BusinessException : {}", businessException);
            throw new BusinessException(businessException.getCode(), businessException.getMessage(), businessException.getHttpStatus());
        } catch (Exception exception) {
            log.error("LoginController : validateLogin : Exception : {}", exception);
            throw new GenericException(exception);
        }
    }

    private String getIpAddress(String clientIPAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(clientIPAddress);
            return "IP Address: " + clientIPAddress + " | Hostname: " + inetAddress.getHostName();
        } catch (UnknownHostException e) {
            return "Unable to get InetAddress for IP: " + clientIPAddress;
        }
    }

}
