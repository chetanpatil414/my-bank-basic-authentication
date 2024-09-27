package com.mybank.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message = (authException != null && authException.getMessage() != null) ? authException.getMessage() : null;
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        response.setHeader("myBank-error-reason", message);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        String jsonResponse = String.format("\"timeStamp\":\"%s\",\"status\":\"%d\",\"error\":\"%s\",\"Message\": \"%s\",\"path\":\"%s\"",
                currentTimeStamp,HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase(),message,request.getRequestURI());

        response.getWriter().write(jsonResponse);

    }
}
