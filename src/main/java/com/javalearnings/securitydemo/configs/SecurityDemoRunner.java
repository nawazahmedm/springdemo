package com.javalearnings.securitydemo.configs;

import com.javalearnings.securitydemo.controllers.MasterLookupController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;

@Configuration
public class SecurityDemoRunner implements CommandLineRunner {

    @Autowired
    private MasterLookupController masterLookupController;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void run(String... args) throws Exception {
        masterLookupController.refreshCache();
    }

}
