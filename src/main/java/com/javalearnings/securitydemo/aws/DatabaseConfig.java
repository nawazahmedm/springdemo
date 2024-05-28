package com.javalearnings.securitydemo.aws;

import com.google.gson.Gson;
import com.javalearnings.securitydemo.model.config.SecretString;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile({"dev", "qa", "uat", "prod"})
@RequiredArgsConstructor
public class DatabaseConfig {

    @Value("${aws.secret.name}")
    private String secretName;

    @Value("${aws.region}")
    private String region;

    @Value("${spring.datasource.driverClassName}")
    private String driver;

    @Bean
    public DataSource dataSource() {
        String secretJson = AwsSecretsManagerService.getSecret(secretName, region);

        // Parse JSON string into DatabaseConfig object
        Gson gson = new Gson();
        SecretString secretString = gson.fromJson(secretJson, SecretString.class);

        String url = "jdbc:" + secretString.getEngine() + "://" + secretString.getHost() + ":" + secretString.getPort() + "/" + secretString.getDbname() + "?zeroDateTimeBehavior=convertToNull";

        // Create and return DataSource with the retrieved credentials
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(secretString.getUsername());
        hikariConfig.setPassword(secretString.getPassword());
        hikariConfig.setDriverClassName(this.driver);

        return new HikariDataSource(hikariConfig);
    }
}
