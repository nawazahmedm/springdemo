package com.javalearnings.securitydemo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Defines the Filter Exception Reasons.
 */
@Getter
@AllArgsConstructor
public enum FilterExceptionReason {

    EXPIRED_JWT_EXCEPTION("JWT expired", HttpStatus.UNAUTHORIZED),

    ILLEGAL_ARGUMENT_EXCEPTION("Token is null, empty or only whitespace", HttpStatus.UNAUTHORIZED),

    MALFORMED_JWT_EXCEPTION("JWT is invalid", HttpStatus.UNAUTHORIZED),

    UNSUPPORTED_JWT_EXCEPTION("JWT is not supported", HttpStatus.UNAUTHORIZED),

    SIGNATURE_EXCEPTION("Signature validation failed", HttpStatus.UNAUTHORIZED),

    GENERIC_JWT_EXCEPTION("JWT Exception", HttpStatus.UNAUTHORIZED);

    private final String code = FilterExceptionReason.class.getSimpleName();
    private final String message;
    private final HttpStatus httpStatus;

}
