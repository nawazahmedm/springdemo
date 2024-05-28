package com.javalearnings.securitydemo.controllers;

import com.javalearnings.securitydemo.exceptions.ErrorResponseDto;
import com.javalearnings.securitydemo.model.login.RequestLoginForm;
import com.javalearnings.securitydemo.model.login.ResponseLoginForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

public interface LoginController {

    /**
     * POST /validateLogin : Login Form
     *
     * @param body Request object to validate the username and password
     * @return Successful Operation (status code 200)
     * or Not Saved due to error. (status code 404)
     */
    @Operation(
            operationId = "validateLogin",
            summary = "Validate Login",
            tags = {"Validate Login"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseLoginForm.class))),
                    @ApiResponse(responseCode = "404", description = "Not Fetched due to error", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/login/validateLogin",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    @CrossOrigin
    ResponseEntity<ResponseLoginForm> validateLogin(HttpServletRequest httpServletRequest,
            @RequestParam(value = "clientIPAddress", required = false) String clientIPAddress,
            @Parameter(name = "body", description = "Request object to Validate Login", required = true,
                    schema = @Schema(description = "")) @Valid @RequestBody RequestLoginForm body
    );





}