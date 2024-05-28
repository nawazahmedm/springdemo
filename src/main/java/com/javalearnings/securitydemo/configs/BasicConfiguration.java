package com.javalearnings.securitydemo.configs;

import com.javalearnings.securitydemo.entities.auth.User;
import com.javalearnings.securitydemo.repositories.auth.UserRepository;
import com.javalearnings.securitydemo.utils.DateUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Slf4j
public class BasicConfiguration {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init(){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("123456");
        log.debug("password {}", password);

        User user = new User();
        user.setUsername("nawaz");
        user.setPassword(password);
        user.setCreatedDate(DateUtils.getLocalDateEST());
        user.setCreatedUserId(1);
        user.setActiveInd("Y");
        user.setFirst("Nawaz");
        user.setUserEmail("test@gmail.com");
        userRepository.save(user);
    }
}
