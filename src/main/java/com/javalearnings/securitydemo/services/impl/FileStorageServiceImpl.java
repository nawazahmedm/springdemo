package com.javalearnings.securitydemo.services.impl;

import com.javalearnings.securitydemo.configs.FileStorageProperties;
import com.javalearnings.securitydemo.constants.BusinessExceptionReason;
import com.javalearnings.securitydemo.entities.file.Document;
import com.javalearnings.securitydemo.entities.file.DocumentContent;
import com.javalearnings.securitydemo.exceptions.FileStorageException;
import com.javalearnings.securitydemo.repositories.file.DocumentContentRepository;
import com.javalearnings.securitydemo.repositories.file.DocumentRepository;
import com.javalearnings.securitydemo.services.FileStorageService;
import com.javalearnings.securitydemo.utils.DateUtils;
import com.javalearnings.securitydemo.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    //private final Path fileStorageLocation;

    private final DocumentRepository documentRepository;

    private final DocumentContentRepository documentContentRepository;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties, DocumentRepository documentRepository,
                                  DocumentContentRepository documentContentRepository) {
        this.documentRepository = documentRepository;
        this.documentContentRepository = documentContentRepository;
        /*this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }*/
    }

    @Override
    public DocumentContent storeFile(Integer documentID, Integer personID, String documentTypeCode, String tabName, MultipartFile file) {
        log.debug("FileStorageServiceImpl : storeFile : Start");
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Document document = null;
            if(personID > 0 && documentID <= 0) {
                documentID = findDocumentID(personID);
            }
            if(documentID > 0) {
                document = documentRepository.findById(documentID).get();
                document.setModifiedBy(personID);
                document.setModifiedDate(DateUtils.getZonedDateEST());
            } else {
                document = new Document();
                document.setCreatedBy(personID);
                document.setCreatedDate(DateUtils.getZonedDateEST());
            }
            document.setPersonId(personID);
            document.setDocumentTypeCode(documentTypeCode);
            document = documentRepository.save(document);
            log.debug("document {}", document);

            DocumentContent documentContent = new DocumentContent();
            Optional<DocumentContent> optionalDocumentContent = documentContentRepository.findById(document.getId());
            if(!optionalDocumentContent.isEmpty() && optionalDocumentContent.get().getDocumentContentID() > 0) {
                log.debug("document 1 {}", document);
                documentContent = optionalDocumentContent.get();
                documentContent.setModifiedBy(personID);
                documentContent.setModifiedDate(DateUtils.getInstantEST());
            } else {
                log.debug("document 2 {}", document);
                documentContent.setDocumentContentID(document.getId());
                documentContent.setCreatedBy(personID);
                documentContent.setCreatedDate(DateUtils.getInstantEST());
            }
            documentContent.setFileName(file.getName());
            documentContent.setFileType(file.getContentType());
            documentContent.setContent(file.getBytes());

            log.debug("FileStorageServiceImpl : storeFile : End");

            return documentContentRepository.save(documentContent);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public ResponseEntity<Resource> downloadFile(Integer documentID, Integer personID) {
        // Load file from database
        DocumentContent documentContent = null;
        if (personID != null && personID > 0) {
            List<DocumentContent> documentContentList = documentContentRepository.findDocumentContentByPersonId(personID);
            if(documentContentList != null && documentContentList.size() > 0) {
                documentContent = documentContentList.get(0);
            }
        } else if(documentID != null && documentID > 0) {
            documentContent = documentContentRepository.findById(documentID).get();
        }

        if(documentContent == null) {
            throw ExceptionUtils.getBusinessException(BusinessExceptionReason.DOCUMENT_NOT_AVAILABLE);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(documentContent.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentContent.getFileName() + "\"")
                .body(new ByteArrayResource(documentContent.getContent()));
    }

    @Override
    public Integer findDocumentID(Integer personID) {
        List<Document> documentList = documentRepository.findByPersonIdAndDocumentTypeCode(personID, "Person");
        if(documentList != null && documentList.size() > 0) {
            return documentList.get(0).getId();
        }
        return 0;
    }

}
