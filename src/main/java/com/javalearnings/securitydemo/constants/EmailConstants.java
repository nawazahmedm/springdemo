package com.javalearnings.securitydemo.constants;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class EmailConstants {

    public final static String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";

    public final static String MAIL_SMTP_AUTH = "mail.smtp.auth";

    public final static String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    public final static String MAIL_DEBUG = "mail.debug";

    public final static String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";

    public final static String MAIL_PROPERTIES_MAIL_SMTP_SSL = "mail.properties.mail.smtp.ssl";

    @Value("${spring.mail.host:PleaseFillInYourApplication}")
    private String host;

    @Value("${spring.mail.port:PleaseFillInYourApplication}")
    private int port;

    @Value("${spring.mail.username:PleaseFillInYourApplication}")
    private String username;

    @Value("${spring.mail.password:PleaseFillInYourApplication}")
    private String password;

    @Value("${spring.mail.properties.mail.transport.protocol:PleaseFillInYourApplication}")
    private String protocol;

    @Value("${spring.mail.properties.mail.smtp.auth:PleaseFillInYourApplication}")
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable:PleaseFillInYourApplication}")
    private String starttlsEnable;

    @Value("${spring.mail.properties.mail.debug:PleaseFillInYourApplication}")
    private String debug;

    @Value("${spring.mail.properties.mail.smtp.socketFactory.class:PleaseFillInYourApplication}")
    private String socketFactoryClass;

    @Value("${spring.mail.properties.mail.smtp.smtp.ssl:PleaseFillInYourApplication}")
    private String ssl;

}
