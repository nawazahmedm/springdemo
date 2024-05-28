package com.javalearnings.securitydemo.exceptions;

import com.javalearnings.securitydemo.utils.DateUtils;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * The type Global error.
 */
@Data
public class GlobalError {
    private HttpStatus httpStatus;
    private LocalDateTime time;
    private String message;

    /**
     * Instantiates a new Global error.
     *
     * @param httpStatus the http status
     * @param message    the message
     */
    public GlobalError(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.time = DateUtils.getLocalDateTimeEST();
        this.message = message;
    }

}
