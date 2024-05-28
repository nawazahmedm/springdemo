package com.javalearnings.securitydemo.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResponseBoolean implements Serializable {

    private Boolean flag;

}
