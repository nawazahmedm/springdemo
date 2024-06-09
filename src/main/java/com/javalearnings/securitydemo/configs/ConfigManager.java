package com.javalearnings.securitydemo.configs;

import com.javalearnings.securitydemo.constants.EmailConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class ConfigManager {

    final EmailConstants emailConstants;

    public ConfigManager(EmailConstants emailConstants) {
        this.emailConstants = emailConstants;
    }

    /**
     * Create Object of RestTemplate Bean
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * Create Object of JavaMailSender Bean
     *
     * @return JavaMailSender
     */
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConstants.getHost());
        mailSender.setPort(emailConstants.getPort());
        mailSender.setUsername(emailConstants.getUsername());
        mailSender.setPassword(emailConstants.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put(EmailConstants.MAIL_TRANSPORT_PROTOCOL, emailConstants.getProtocol());
        props.put(EmailConstants.MAIL_SMTP_AUTH, emailConstants.getAuth());
        props.put(EmailConstants.MAIL_SMTP_STARTTLS_ENABLE, emailConstants.getStarttlsEnable());
        props.put(EmailConstants.MAIL_DEBUG, emailConstants.getDebug());
        props.put(EmailConstants.MAIL_SMTP_SOCKET_FACTORY_CLASS, emailConstants.getSocketFactoryClass());
        props.put(EmailConstants.MAIL_PROPERTIES_MAIL_SMTP_SSL, emailConstants.getSsl());

        return mailSender;
    }

}
