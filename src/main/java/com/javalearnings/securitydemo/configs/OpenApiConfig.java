package com.javalearnings.securitydemo.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "${api.title}", version = "${api.version}",
                contact = @Contact(name = "${api.developer.contact}", email = "${api.developer.email}", url = "${api.testing.url}"),
                license = @License(name = "${apache.name}", url = "${apache.url}"),
                termsOfService = "${tos.uri}",
                description = "${api.description}"),
        servers = {
                @Server(url = "${local.server.url}", description = "Development"),
                @Server(url = "${api.staging.server.url}", description = "Staging"),
                @Server(url = "${api.prod.server.url}", description = "Production")
        }
)
public class OpenApiConfig {

    /**
     * Configure the OpenAPI components.
     *
     * @return Returns fully configure OpenAPI object
     * @see OpenAPI
     */
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .description(
                                        "Provide the JWT token. JWT token can be obtained from the Login API.")
                                .bearerFormat("JWT")));
    }

}
