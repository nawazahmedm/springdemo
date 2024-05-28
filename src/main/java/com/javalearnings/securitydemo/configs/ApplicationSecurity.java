package com.javalearnings.securitydemo.configs;

import com.javalearnings.securitydemo.filters.JwtTokenFilter;
import com.javalearnings.securitydemo.repositories.auth.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class ApplicationSecurity {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    public static final String[] AUTH_WHITELIST = {
            "/admin/", "/css/**", "/js/**", "/fonts/**", "/api/**", "/h2-console/**", "/h2-console/",
            "/login/",
            "/auth/login",
            "/docs/**",
            "/users",
            // -- swagger ui
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger-ui/",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/favicon.ico",
            "/swagger-ui/index.html/**",
            "/webjars/springfox-swagger-ui/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/swagger-ui/**", "/javalearnings-openapi/**", "/swagger-ui-custom.html" ,"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**"
    };

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("User " + username + " not found"));
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.permitAll())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.deleteCookies("remember-me", "JSESSIONID").permitAll())
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
                .headers(httpSecurityHeadersConfigurer -> {
                    httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable());
                    httpSecurityHeadersConfigurer.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"));
                    httpSecurityHeadersConfigurer.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE"));
                    httpSecurityHeadersConfigurer.addHeaderWriter(new StaticHeadersWriter("Access-Control-Max-Age", "3600"));
                    httpSecurityHeadersConfigurer.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"));
                    httpSecurityHeadersConfigurer.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "*"));
                });

        http.authorizeHttpRequests(
                authorizationManagerRequestMatcherRegistry -> {
                    try {
                        List<String> authList = Arrays.asList(AUTH_WHITELIST);
                        authList.forEach(
                                item -> authorizationManagerRequestMatcherRegistry
                                        .requestMatchers(antMatcher(item)).permitAll()
                        );
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(antMatcher(HttpMethod.TRACE, "/**")).denyAll()
                                //.requestMatchers(antMatcher("/admin/**")).authenticated()
                                .anyRequest().permitAll();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        http.exceptionHandling(
                httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
                            (request, response, ex) -> {
                                response.sendError(
                                        HttpServletResponse.SC_UNAUTHORIZED,
                                        ex.getMessage()
                                );
                            }
                    );
                }
        );

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}