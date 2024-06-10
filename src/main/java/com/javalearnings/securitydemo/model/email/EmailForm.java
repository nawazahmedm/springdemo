package com.javalearnings.securitydemo.model.email;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailForm implements Serializable {

    private String to;

    private String cc;

    private String body;

    private String subject;

}
