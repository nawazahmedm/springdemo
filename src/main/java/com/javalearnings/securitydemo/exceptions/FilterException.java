package com.javalearnings.securitydemo.exceptions;

import com.javalearnings.securitydemo.constants.FilterExceptionReason;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
@Setter
public class FilterException extends AuthenticationException {

    protected String code;
    protected String message;
    protected HttpStatus httpStatus;

    public FilterException(String unAuthorised) {
        super(unAuthorised);
        this.message = unAuthorised;
    }

    public FilterException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public FilterException(FilterExceptionReason expiredJwtException, String code) {
        super(expiredJwtException.getMessage());
        this.code = expiredJwtException.getCode();
        this.message = expiredJwtException.getMessage();
        this.httpStatus = expiredJwtException.getHttpStatus();
    }
}
