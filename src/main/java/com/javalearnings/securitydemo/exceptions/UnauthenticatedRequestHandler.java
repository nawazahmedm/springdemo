package com.javalearnings.securitydemo.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

@Component
@Slf4j
public class UnauthenticatedRequestHandler implements AuthenticationEntryPoint {
    @Autowired
    private MessageSource messageSource;

    public void commence(HttpServletRequest request, HttpServletResponse response, FilterException authException) throws IOException {
        log.error(authException.toString());

        String message = messageSource.getMessage(authException.getMessage(), null, null, Locale.getDefault());

        if (message == null) message = authException.getMessage();

        ResponseDTO responseDTO = new ResponseDTO(authException.getCode(), message, authException.getHttpStatus().value());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(responseDTO);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(jsonString);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error(authException.toString());

        String message = messageSource.getMessage(authException.getMessage(), null, null, Locale.getDefault());

        if (message == null) message = authException.getMessage();

        ResponseDTO responseDTO = new ResponseDTO("UNAUTHORIZED", message, HttpStatus.UNAUTHORIZED.value());

        String jsonString = new ObjectMapper().writeValueAsString(responseDTO);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(jsonString);
    }
}