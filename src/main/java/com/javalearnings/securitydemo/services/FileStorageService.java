package com.javalearnings.securitydemo.services;

import com.javalearnings.securitydemo.entities.file.DocumentContent;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    DocumentContent storeFile(Integer documentID, Integer personID, String documentTypeCode, String tabName, MultipartFile file);

    ResponseEntity<Resource> downloadFile(Integer documentID, Integer personID);

    Integer findDocumentID(Integer personID);

}
