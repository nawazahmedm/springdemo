package com.javalearnings.securitydemo.model.config;

import lombok.Data;

@Data
public class SecretString {
    private String username;
    private String password;
    private String engine;
    private String host;
    private String port;
    private String dbname;
}

