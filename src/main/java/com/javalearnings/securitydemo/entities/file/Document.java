package com.javalearnings.securitydemo.entities.file;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Table(name = "documents")
@Data
public class Document {

    @Id
    @Column(name = "DocumentID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 24)
    @Column(name = "DocumentTypeCode", length = 24)
    private String documentTypeCode;

    @Column(name = "PersonId")
    private Integer personId;

    @Column(name = "ValidUntil")
    private ZonedDateTime validUntil;

    @Size(max = 45)
    @Column(name = "Documentscol", length = 45)
    private String documentscol;

    @Column(name = "CreatedDate")
    private ZonedDateTime createdDate;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "ModifiedDate")
    private ZonedDateTime modifiedDate;

    @Column(name = "ModifiedBy")
    private Integer modifiedBy;

}
