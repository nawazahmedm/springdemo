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
public class GenericException extends RuntimeException {

    protected String code;
    protected String message;
    protected HttpStatus httpStatus;

    /**
     * Constructor accepting an exception reason.
     *
     * @param code add code
     * @param message add message
     * @param httpStatus add http status code
     */
    public GenericException(final String code, final String message, final HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Constructor
     *
     * @param exception throws Exception
     */
    public GenericException(final Exception exception) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        this.message = exception.toString();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Constructor accepting an excepting reason and optional parameters which are replaced in the message
     *
     * @param code add code
     * @param message add message
     * @param httpStatus add http status code
     * @param parameters add parameters
     */
    public GenericException(final String code, final String message, final HttpStatus httpStatus, final Object... parameters) {
        if (parameters != null) {
            this.message = format(message, parameters);
        } else {
            this.message = message;
        }

        this.code = code;
        this.httpStatus = httpStatus;
    }

    /**
     * Convert the given object to string
     */
    public String toString() {
        return format("GenericException(code=%s, message=%s, httpStatus=%s)", this.getCode(), this.getMessage(),
            this.getHttpStatus().value());
    }

}
