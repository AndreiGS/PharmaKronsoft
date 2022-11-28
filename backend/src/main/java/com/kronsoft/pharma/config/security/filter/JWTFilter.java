package com.kronsoft.pharma.config.security.filter;

import com.kronsoft.pharma.auth.AuthToken;
import com.kronsoft.pharma.auth.AuthTokenRepository;
import com.kronsoft.pharma.auth.util.PathChecker;
import com.kronsoft.pharma.config.security.util.TokenUtil;
import com.kronsoft.pharma.util.AuthenticationUtil;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    private final TokenUtil tokenUtil;
    private final AuthTokenRepository authTokenRepository;
    private final PathChecker pathChecker;
    private final AuthenticationUtil authenticationUtil;

    @Autowired
    public JWTFilter(TokenUtil tokenUtil, AuthTokenRepository authTokenRepository, PathChecker pathChecker, AuthenticationUtil authenticationUtil) {
        this.tokenUtil = tokenUtil;
        this.authTokenRepository = authTokenRepository;
        this.pathChecker = pathChecker;
        this.authenticationUtil = authenticationUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (pathChecker.isPermitAllPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = tokenUtil.getJWTFromHeader(request);
        AuthToken authToken = authTokenRepository.findByJwtToken(jwt).orElseThrow(() -> new MalformedJwtException("JWT invalid"));

        if (!tokenUtil.JWT_isValid(jwt)) {
            logger.error("Cannot set user authentication: JWT expired");
            throw new MalformedJwtException("JWT expired");
        }

        setAuthToken(authToken);

        filterChain.doFilter(request, response);
    }

    private void setAuthToken(AuthToken authToken) {
        authenticationUtil.authenticate(authToken.getJwtToken());
        AuthenticationUtil.getUserDetails().setActiveAuthToken(authToken);
    }
}
