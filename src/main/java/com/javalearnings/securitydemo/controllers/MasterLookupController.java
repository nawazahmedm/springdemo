package com.javalearnings.securitydemo.controllers;

import com.javalearnings.securitydemo.exceptions.ErrorResponseDto;
import com.javalearnings.securitydemo.model.common.ResponseBoolean;
import com.javalearnings.securitydemo.model.common.ResponseSelect;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface MasterLookupController {

    /**
     * GET /fetchList : Returns the List of the Requested List
     *
     * @return Response Object (status code 200)
     *         or List not Fetched due to error. (status code 404)
     */
    @Operation(
            operationId = "fetchListWitParam",
            summary = "Returns the List of the Requested List",
            tags = { "Returns the List of the Requested List" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =  ResponseSelect.class))),
                    @ApiResponse(responseCode = "404", description =  "List not Fetched due to error", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =  ErrorResponseDto.class)))
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/master/fetchStateList"},
            produces = { "application/json" }
    )
    @CrossOrigin
    ResponseEntity<ResponseSelect> fetchListWithParam(@RequestHeader(value = "UserId", required = true) Integer userId,
                                                     @RequestParam(value = "id", required = true) Integer id,
                                             HttpServletRequest httpServletRequest);

    /**
     * GET /fetchList : Returns the List of the Requested List
     *
     * @return Response Object (status code 200)
     *         or List not Fetched due to error. (status code 404)
     */
    @Operation(
            operationId = "fetchList",
            summary = "Returns the List of the Requested List",
            tags = { "Returns the List of the Requested List" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =  ResponseSelect.class))),
                    @ApiResponse(responseCode = "404", description =  "List not Fetched due to error", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =  ErrorResponseDto.class)))
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/master/fetchCountryList", "/master/fetchRolesList", "/master/fetchActiveInd", "/master/YesOrNo", "/master/accountingType", "/master/renewalDate","/master/dayOfpayment"},
            produces = { "application/json" }
    )
    @CrossOrigin
    ResponseEntity<ResponseSelect> fetchList(@RequestHeader(value = "UserId", required = true) Integer userId,
                                             HttpServletRequest httpServletRequest);

    /**
     * GET /refreshCache : Return Boolean value
     *
     * @return Response Object (status code 200)
     *         or Does not execute due to error. (status code 404)
     */
    @Operation(
            operationId = "refreshCache",
            summary = "Return Boolean",
            tags = { "Return Boolean" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =  ResponseBoolean.class))),
                    @ApiResponse(responseCode = "404", description =  "Does not execute due to error", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =  ErrorResponseDto.class)))
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/master/refreshCache",
            produces = { "application/json" }
    )
    @CrossOrigin
    ResponseEntity<ResponseBoolean> refreshCache();
    
}
