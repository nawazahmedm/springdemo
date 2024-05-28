package com.javalearnings.securitydemo.filters;

import com.javalearnings.securitydemo.configs.ApplicationSecurity;
import com.javalearnings.securitydemo.constants.FilterExceptionReason;
import com.javalearnings.securitydemo.entities.auth.User;
import com.javalearnings.securitydemo.exceptions.FilterException;
import com.javalearnings.securitydemo.exceptions.UnauthenticatedRequestHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Stream;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private UnauthenticatedRequestHandler unauthenticatedRequestHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (!hasAuthorizationBearer(request)) {
                log.debug("JwtTokenFiler hasAuthorizationBearer");
                filterChain.doFilter(request, response);
                return;
            }

            String token = getAccessToken(request);
            Integer userId = getUserId(request);

            if (!jwtUtil.validateAccessToken(token, userId)) {
                filterChain.doFilter(request, response);
                return;
            }

            setAuthenticationContext(token, request);
            filterChain.doFilter(request, response);
        } catch (FilterException authenticationException) {
            unauthenticatedRequestHandler.commence(request, response, authenticationException);
        }
    }

    /*
    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String path = request.getRequestURI();
        log.debug("JwtTokenFiler : path {}", path);
        log.debug("JwtTokenFiler : header {}", header);
        boolean flag = (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer"));
            //&& Stream.of(ApplicationSecurity.AUTH_WHITELIST).anyMatch(x -> path.contains(x));
        log.debug("JwtTokenFiler : flag {}", flag);

        if (flag) {
            return false;
        }

        return true;
    }*/

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String path = request.getRequestURI();
        log.debug("JwtTokenFiler : path {}", path);
        log.debug("JwtTokenFiler : header {}", header);
        boolean flag = Stream.of(ApplicationSecurity.AUTH_WHITELIST).anyMatch(x -> path.contains(x));
        if (!flag) {
            if (!ObjectUtils.isEmpty(header) && header.startsWith("Bearer")) {
                return true;
            } else {
                throw new FilterException(FilterExceptionReason.ILLEGAL_ARGUMENT_EXCEPTION, "ILLEGAL_ARGUMENT_EXCEPTION");
            }
        }
        return false;
    }

    private String getAccessToken(HttpServletRequest request) throws FilterException {
        String header = request.getHeader("Authorization");
        String token;
        if (header != null) {
            token = header.split(" ")[1].trim();
        } else {
            throw new FilterException(FilterExceptionReason.ILLEGAL_ARGUMENT_EXCEPTION, "ILLEGAL_ARGUMENT_EXCEPTION");
        }
        return token;
    }

    private Integer getUserId(HttpServletRequest request) throws FilterException {
        return request.getIntHeader("UserId");
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {
        User user = new User();
        String[] jwtSubject = jwtUtil.getSubject(token).split(",");

        user.setUserId(Integer.parseInt(jwtSubject[0]));
        user.setUsername(jwtSubject[1]);

        return user;
    }
}
