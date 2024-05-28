package com.javalearnings.securitydemo.services;

import com.javalearnings.securitydemo.model.login.RequestLoginForm;
import com.javalearnings.securitydemo.model.login.ResponseLoginForm;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<ResponseLoginForm> validateLogin(RequestLoginForm requestLoginForm);

}
