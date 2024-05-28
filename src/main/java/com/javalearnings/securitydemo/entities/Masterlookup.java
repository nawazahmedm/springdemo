package com.javalearnings.securitydemo.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "masterlookup")
@Data
public class Masterlookup {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "CodeType", nullable = false, length = 100)
    private String codeType;

    @Size(max = 24)
    @NotNull
    @Column(name = "Code", nullable = false, length = 24)
    private String code;

    @Size(max = 60)
    @Column(name = "Description", length = 60)
    private String description;

    @Size(max = 1)
    @NotNull
    @Column(name = "SystemFlag", nullable = false, length = 1)
    private String systemFlag;

    @Column(name = "ActiveFlag")
    private Integer activeFlag;

    @NotNull
    @Column(name = "CreatedDate", nullable = false)
    private ZonedDateTime createdDate;

    @NotNull
    @Column(name = "CreatedBy", nullable = false)
    private Integer createdBy;

    @Column(name = "ModifiedDate")
    private ZonedDateTime modifiedDate;

    @Column(name = "ModifiedBy")
    private Integer modifiedBy;

}
