package com.javalearnings.securitydemo.model.login;

import java.io.Serializable;

public record ResponseLoginForm(long userId, String token) implements Serializable {
}
