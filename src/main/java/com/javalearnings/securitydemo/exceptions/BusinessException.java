package com.javalearnings.securitydemo.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;

/**
 * Custom exception class to use in case of a business exception.
 */
@Getter
@Setter
public class BusinessException extends GenericException {

    /**
     * Constructor
     *
     * @param exception throws Exception
     */
    public BusinessException(Exception exception) {
        super(exception);
    }

    /**
     * Constructor with 3 parameters
     *
     * @param code add code
     * @param message add message
     * @param httpStatus add http status code
     */
    public BusinessException(String code, String message, HttpStatus httpStatus) {
        super(code, message, httpStatus);
    }

    /**
     * Constructor with 4 parameters
     *
     * @param code add code
     * @param message add message
     * @param httpStatus add http status code
     * @param parameters add parameters
     */
    public BusinessException(final String code, final String message, final HttpStatus httpStatus, final Object... parameters) {
        super(code, message, httpStatus, parameters);
    }

    /**
     * Convert the given object to string
     */
    public String toString() {
        return format("BusinessException(code=%s, message=%s, httpStatus=%s)", this.getCode(), this.getMessage(),
            this.getHttpStatus().value());
    }

}
