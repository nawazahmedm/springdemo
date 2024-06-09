package com.javalearnings.securitydemo.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * GlobalExceptionHandler will catch validation failures and return a custom HTTP Status 400 Bad Request response.
 *
 * @author Nawaz Mohammed
 * @since 2022-02-25
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles the uncaught {@link MethodArgumentNotValidException} exceptions and returns a JSON formatted response.
     *
     * @param ex      the ex
     * @param headers headers
     * @param status  HttpStatusCode
     * @param request the request on which the ex occurred
     * @return a JSON formatted response containing the ex details and additional fields
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log(ex, (ServletWebRequest) request);
        final List<InvalidParameterDto> invalidParameters = ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> InvalidParameterDto.builder()
                .parameter(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build()).collect(toList());

        final ErrorResponseDto errorResponseDto = ErrorResponseUtil.build(MethodArgumentNotValidException.class.getSimpleName(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST, invalidParameters);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    /**
     * handleConstraintViolationException will catch constraint violations thrown when model validation fails. This can be caused by the end user not providing
     * expected values in the request body, which will then cause SpringBoot to throw this exception. We catch the exception and return a custom response to
     * indicate that the validation failed.
     *
     * @param e the exception that was thrown
     * @return a custom ResponseEntity with the error message, as well as the HTTP Status 400 Bad Request
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e){
        log.warn("Validation failed to parse request body", e);
        GlobalError error = new GlobalError(HttpStatus.BAD_REQUEST,"There was an issue in handling your request. Verify that required fields are valid.");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the uncaught {@link BusinessException} exceptions and returns a JSON formatted response.
     *
     * @param ex      the ex
     * @param request the request on which the ex occurred
     * @return a JSON formatted response containing the ex details and additional fields
     */
    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Object> handleCustomUncaughtBusinessException(final BusinessException ex,
                                                                        final ServletWebRequest request) {
        log(ex, request);
        final ErrorResponseDto errorResponseDto = ErrorResponseUtil.build(ex.getCode(), ex.getMessage(), ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponseDto);
    }

    /**
     * Handles the uncaught {@link GenericException} exceptions and returns a JSON formatted response.
     *
     * @param ex      the ex
     * @param request the request on which the ex occurred
     * @return a JSON formatted response containing the ex details and additional fields
     */
    @ExceptionHandler({GenericException.class})
    public ResponseEntity<Object> handleCustomUncaughtGenericException(final GenericException ex,
                                                                        final ServletWebRequest request) {
        log(ex, request);
        final ErrorResponseDto errorResponseDto = ErrorResponseUtil.build(ex.getCode(), ex.getMessage(), ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponseDto);
    }

    private void log(final Exception ex, final ServletWebRequest request) {
        final Optional<HttpMethod> httpMethod;
        final Optional<String> requestUrl;

        final Optional<ServletWebRequest> possibleIncomingNullRequest = Optional.ofNullable(request);
        if (possibleIncomingNullRequest.isPresent()) {
            // get the HTTP Method
            httpMethod = Optional.ofNullable(possibleIncomingNullRequest.get().getHttpMethod());
            // get the Request URL
            requestUrl = Optional.of(possibleIncomingNullRequest.get().getRequest().getRequestURL().toString());
        } else {
            httpMethod = Optional.empty();
            requestUrl = Optional.empty();
        }

        log.error("Request {} {} failed with exception reason: {}", httpMethod.isPresent() ? httpMethod.get() : "'null'",
            requestUrl.orElse("'null'"), ex.getMessage(), ex);
    }
}
