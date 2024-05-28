package com.javalearnings.securitydemo.utils;

import com.javalearnings.securitydemo.constants.BusinessExceptionReason;
import com.javalearnings.securitydemo.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ExceptionUtils {

    /**
     * Create BusinessException object
     *
     * @param businessExceptionReason BusinessExceptionReason
     * @return BusinessException
     */
    public static BusinessException getBusinessException(final BusinessExceptionReason businessExceptionReason){
        return new BusinessException(businessExceptionReason.getCode()
                , businessExceptionReason.getMessage(), businessExceptionReason.getHttpStatus());
    }

    /**
     * Create BusinessException object
     *
     * @param businessExceptionReason BusinessExceptionReason
     * @param httpStatus HttpStatus
     * @return BusinessException
     */
    public static BusinessException getBusinessException(final BusinessExceptionReason businessExceptionReason, final String httpStatus){
        return new BusinessException(businessExceptionReason.getCode()
                , businessExceptionReason.getMessage(), businessExceptionReason.getHttpStatus(), httpStatus);
    }

    /**
     * Creates BusinessException object
     *
     * @param httpStatus HttpStatus
     * @param body Body
     * @return BusinessException
     */
    public static BusinessException getCustomException(final HttpStatus httpStatus, String body){
        return new BusinessException(httpStatus.toString()
                    , body, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Creates BusinessException object
     *
     * @param message Message
     * @return BusinessException
     */
    public static BusinessException getCustomException(final String message){
        return new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.toString()
                , message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
