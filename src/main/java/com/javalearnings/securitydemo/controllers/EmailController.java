package com.javalearnings.securitydemo.controllers;

import com.javalearnings.securitydemo.exceptions.ErrorResponseDto;
import com.javalearnings.securitydemo.model.common.ResponseBoolean;
import com.javalearnings.securitydemo.model.email.EmailForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface EmailController {

    /**
     * POST /validateLogin : Login Form
     *
     * @param body Request object to validate the username and password
     * @return Successful Operation (status code 200)
     * or Not Saved due to error. (status code 404)
     */
    @Operation(
            operationId = "sendEmail",
            summary = "sendEmail",
            tags = {"SendEmail"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseBoolean.class))),
                    @ApiResponse(responseCode = "404", description = "Not Fetched due to error", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/email/sendEmail",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @CrossOrigin
    ResponseEntity<ResponseBoolean> sendEmail(@RequestHeader(value = "UserId", required = true) Integer userId,
                                              @Parameter(name = "body", description = "Request object to Validate Login", required = true,
                                                            schema = @Schema(description = "")) @Valid @RequestBody EmailForm body
    );

}
