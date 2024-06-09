package com.javalearnings.securitydemo.controllers;

import com.javalearnings.securitydemo.exceptions.ErrorResponseDto;
import com.javalearnings.securitydemo.model.file.UploadFileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

public interface FileController {

    /**
     * POST /uploadFile
     *
     * @param file
     * @return Successful Operation (status code 200)
     * or Not Fetched due to error. (status code 404)
     */
    @Operation(
            operationId = "uploadFile",
            summary = "Upload File",
            tags = { "Upload File" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =  UploadFileResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Uploaded due to server error", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =  ErrorResponseDto.class)))
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/file/uploadFile",
            produces = { "application/json" },
            consumes = { "multipart/form-data" }
    )
    @CrossOrigin
    UploadFileResponse uploadFile(@RequestHeader(value = "UserId", required = true) Integer userId,
                                  @Parameter(name = "body", description = "Upload File", required = true,
            schema = @Schema(description = "")) @RequestParam("file") @RequestBody MultipartFile file,
                                  @RequestParam Integer documentID, @RequestParam Integer personID,
                                  @RequestParam String documentTypeCode, @RequestParam String tabName);

    /**
     * GET /downloadFile/{fileId}
     *
     * @return Successful Operation (status code 200)
     * or File not Downloaded due to server error (status code 404)
     */
    @Operation(
            operationId = "downloadFile",
            summary = "Download File",
            tags = { "Download File" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =  Resource.class))),
                    @ApiResponse(responseCode = "404", description = "File not Downloaded due to server error", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =  ErrorResponseDto.class)))
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "file/downloadFile",
            produces = { "application/json" }
    )
    @CrossOrigin
    ResponseEntity<Resource> downloadFile(@RequestHeader(value = "UserId", required = true) Integer userId,
                                          @RequestParam Integer documentID, @RequestParam Integer personID);

}
