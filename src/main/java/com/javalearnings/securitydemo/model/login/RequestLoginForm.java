package com.javalearnings.securitydemo.model.login;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestLoginForm implements Serializable {

    private String username;

    private String password;

    private String newPassword;

}
