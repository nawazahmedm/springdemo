package com.javalearnings.securitydemo.controllers.impl;

import com.javalearnings.securitydemo.controllers.FileController;
import com.javalearnings.securitydemo.entities.file.DocumentContent;
import com.javalearnings.securitydemo.model.file.UploadFileResponse;
import com.javalearnings.securitydemo.services.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Slf4j
public class FileControllerImpl implements FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileControllerImpl.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public UploadFileResponse uploadFile(Integer userId, MultipartFile file, Integer documentID, Integer personID, String documentTypeCode, String tabName) {
        log.debug("FileControllerImpl : UploadFileResponse : Start");
        System.out.println("UploadFileResponse method");
        DocumentContent documentContent = fileStorageService.storeFile(documentID, personID, documentTypeCode, tabName, file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(documentContent.getFileName())
                .toUriString();

        UploadFileResponse uploadFileResponse = new UploadFileResponse(documentContent.getDocumentContentID(), personID, documentContent.getFileName(),
                fileDownloadUri, file.getContentType(), file.getSize());
        log.debug("FileControllerImpl : UploadFileResponse : End");
        return uploadFileResponse;
    }

    @Override
    public ResponseEntity<Resource> downloadFile(Integer userId, Integer documentID, Integer personID) {
        return fileStorageService.downloadFile(documentID, personID);
    }

}