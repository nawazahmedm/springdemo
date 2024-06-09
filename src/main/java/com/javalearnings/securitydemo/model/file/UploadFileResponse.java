package com.javalearnings.securitydemo.model.file;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileResponse {

    private Integer documentID;
    private Integer personId;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

}