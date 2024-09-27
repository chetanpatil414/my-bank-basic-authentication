package com.mybank.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        if(csrfToken != null){
            Cookie csrfCookie = new Cookie("XSRF-TOKEN",csrfToken.getToken());
            csrfCookie.setPath("/"); // Set the path of the cookie to be the root
            csrfCookie.setHttpOnly(false); // Allow JavaScript access (can be true if using HttpOnly)
            csrfCookie.setSecure(true); // Set true if using HTTPS
            csrfCookie.setMaxAge(-1); // Session-based cookie

            response.addCookie(csrfCookie);
        }
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
