package com.kronsoft.pharma.config.security.filter;

import com.kronsoft.pharma.config.security.util.TokenConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Expose-Headers", TokenConstants.JWT_HEADER + ", " + TokenConstants.REFRESH_HEADER);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        filterChain.doFilter(request, response);
    }
}
