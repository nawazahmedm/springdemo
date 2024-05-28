package com.javalearnings.securitydemo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO implements Serializable {

    String code;

    String message;

    Integer status;

}
