package com.kronsoft.pharma.config.security.filter;

import com.kronsoft.pharma.auth.AuthTokenRepository;
import com.kronsoft.pharma.auth.util.PathChecker;
import com.kronsoft.pharma.config.security.MyUserDetails;
import com.kronsoft.pharma.config.security.exception.RFTExpiredException;
import com.kronsoft.pharma.config.security.util.TokenUtil;
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
        if (pathChecker.isPermitAllPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String rftToken = tokenUtil.getRefreshHeader(request);
        if (!tokenUtil.RFT_isValid(rftToken) || authTokenRepository.findByRefreshToken(rftToken).isEmpty()) {
            logger.error("Cannot set user authentication: RFT expired");
            throw new RFTExpiredException();
        }

        filterChain.doFilter(request, response);
    }
}
