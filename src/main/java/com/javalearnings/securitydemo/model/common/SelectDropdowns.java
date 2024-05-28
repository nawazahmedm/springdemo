package com.javalearnings.securitydemo.model.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class SelectDropdowns implements Serializable {

    private String code;

    private String description;

}
