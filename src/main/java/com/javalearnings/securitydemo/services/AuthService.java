package com.javalearnings.securitydemo.services;

import com.javalearnings.securitydemo.model.auth.AuthResponse;

public interface AuthService {

    AuthResponse generateTokenUsingUsername(String username, String password);

}
