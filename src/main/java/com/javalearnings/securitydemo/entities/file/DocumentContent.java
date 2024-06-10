package com.javalearnings.securitydemo.entities.file;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Table(name = "documentcontent")
@Data
public class DocumentContent {

    @Id
    @Column(name = "DocumentContentID", nullable = false)
    private Integer documentContentID;

    @Size(max = 50)
    @Column(name = "FileType", length = 50)
    private String fileType;

    @Lob
    @Column(name = "Content")
    private byte[] content;

    @Column(name = "CreatedDate")
    private ZonedDateTime createdDate;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "ModifiedDate")
    private ZonedDateTime modifiedDate;

    @Column(name = "ModifiedBy")
    private Integer modifiedBy;

    @Size(max = 100)
    @NotNull
    @Column(name = "FileName", nullable = false, length = 100)
    private String fileName;

}
