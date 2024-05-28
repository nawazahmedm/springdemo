package com.javalearnings.securitydemo.services.impl;

import com.javalearnings.securitydemo.constants.BusinessExceptionReason;
import com.javalearnings.securitydemo.entities.auth.User;
import com.javalearnings.securitydemo.model.auth.AuthResponse;
import com.javalearnings.securitydemo.model.login.RequestLoginForm;
import com.javalearnings.securitydemo.model.login.ResponseLoginForm;
import com.javalearnings.securitydemo.repositories.auth.UserRepository;
import com.javalearnings.securitydemo.services.AuthService;
import com.javalearnings.securitydemo.services.LoginService;
import com.javalearnings.securitydemo.utils.BaseEncodeUtils;
import com.javalearnings.securitydemo.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final AuthService authService;

    private final BaseEncodeUtils baseEncodeUtils;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<ResponseLoginForm> validateLogin(RequestLoginForm requestLoginForm) {
        log.debug("LoginServiceImpl : validateLogin : requestLoginForm Start {}", requestLoginForm);
        if (StringUtils.isNotBlank(requestLoginForm.getPassword())) {
            requestLoginForm.setPassword(baseEncodeUtils.getDecodedText(requestLoginForm.getPassword()));
        }
        if (StringUtils.isNotBlank(requestLoginForm.getNewPassword())) {
            requestLoginForm.setNewPassword(baseEncodeUtils.getDecodedText(requestLoginForm.getNewPassword()));
        }
        User user = getUser(requestLoginForm);
        if (user != null) {
            String password = user.getPassword();
            if (password != null) {
                boolean pass = this.passwordEncoder.matches(requestLoginForm.getPassword(), password);
                if (!pass) {
                    throw ExceptionUtils.getBusinessException(BusinessExceptionReason.PASSWORD_INVALID);
                }
            }
        } else {
            throw ExceptionUtils.getBusinessException(BusinessExceptionReason.USER_NOT_EXISTS);
        }

        log.debug("requestLoginForm {}", requestLoginForm);
        AuthResponse authResponse = authService.generateTokenUsingUsername(requestLoginForm.getUsername(), requestLoginForm.getPassword());
        log.debug("LoginServiceImpl : validateLogin : responseEntity End {}", authResponse);

        return new ResponseEntity<>(new ResponseLoginForm(user.getUserId(), authResponse.getAccessToken()), HttpStatus.OK);
    }

    private User getUser(RequestLoginForm requestLoginForm) {
         Optional<User> optionalUser = userRepository.findByUsername(requestLoginForm.getUsername());
         if (optionalUser.isPresent()) {
            return optionalUser.get();
         }
         return null;
    }

}
