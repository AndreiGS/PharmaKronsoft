package com.kronsoft.pharma.security.filter;

import com.kronsoft.pharma.auth.AuthToken;
import com.kronsoft.pharma.auth.AuthTokenRepository;
import com.kronsoft.pharma.security.util.PathChecker;
import com.kronsoft.pharma.security.exception.RFTExpiredException;
import com.kronsoft.pharma.security.util.TokenUtil;
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
import java.util.Objects;

@Component
public class RefreshTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenFilter.class);
    private final TokenUtil tokenUtil;
    private final AuthTokenRepository authTokenRepository;
    private final PathChecker pathChecker;

    @Autowired
    public RefreshTokenFilter(TokenUtil tokenUtil, AuthTokenRepository authTokenRepository, PathChecker pathChecker) {
        this.tokenUtil = tokenUtil;
        this.authTokenRepository = authTokenRepository;
        this.pathChecker = pathChecker;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String rftToken = tokenUtil.getRefreshFromHeader(request);
        AuthToken authToken = authTokenRepository.findByRefreshToken(rftToken).orElseThrow(RFTExpiredException::new);

        if (!Objects.equals(authToken.getJwtToken(), tokenUtil.getJWTFromHeader(request))) {
            logger.error("Cannot set user authentication: JWT and RFT do not match");
            throw new MalformedJwtException("JWT expired");
        }

        if (!tokenUtil.RFT_isValid(rftToken)) {
            logger.error("Cannot set user authentication: RFT expired");
            throw new RFTExpiredException();
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return pathChecker.isPermitAllPath(request);
    }
}