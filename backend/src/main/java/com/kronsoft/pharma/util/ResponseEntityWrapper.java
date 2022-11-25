package com.kronsoft.pharma.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kronsoft.pharma.auth.AuthToken;
import com.kronsoft.pharma.auth.AuthTokenRepository;
import com.kronsoft.pharma.config.security.MyUserDetails;
import com.kronsoft.pharma.config.security.util.TokenConstants;
import com.kronsoft.pharma.config.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;

@Component
public class ResponseEntityWrapper<T> extends ResponseEntity<T> {
    @JsonIgnore
    private static AuthTokenRepository k_authTokenRepository;
    @JsonIgnore
    @Autowired
    private AuthTokenRepository authTokenRepository;

    @JsonIgnore
    private static TokenUtil k_tokenUtil;
    @JsonIgnore
    @Autowired
    private TokenUtil tokenUtil;

    @PostConstruct
    private void initializeBeans() {
        ResponseEntityWrapper.k_authTokenRepository = this.authTokenRepository;
        ResponseEntityWrapper.k_tokenUtil = this.tokenUtil;
    }

    private static<T> ResponseEntity<T> build(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        AuthToken authToken = init();
        if (authToken == null) {
            return new ResponseEntity<>(body, headers, status);
        }
        headers.add(TokenConstants.JWT_HEADER, authToken.getJwtToken());
        headers.add(TokenConstants.REFRESH_HEADER, authToken.getRefreshToken());
        return new ResponseEntity<>(body, headers, status);
    }

    private static<T> ResponseEntity<T> build(T body, HttpStatus status) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        return ResponseEntityWrapper.build(body, headers, status);
    }

    public ResponseEntityWrapper() {
        this(null, HttpStatus.OK);
    }

    public ResponseEntityWrapper(T body, HttpStatus status) {
        this(build(body, status));
    }

    public ResponseEntityWrapper(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        this(build(body, headers, status));
    }

    private ResponseEntityWrapper(ResponseEntity<T> responseEntity) {
        super(responseEntity.getBody(), responseEntity.getHeaders(), responseEntity.getStatusCode());
    }

    private static AuthToken init() {
        if (ResponseEntityWrapper.k_authTokenRepository == null || ResponseEntityWrapper.k_tokenUtil == null) {
            return null;
        }

        MyUserDetails userDetails = AuthenticationUtil.getUserDetails();
        if (userDetails == null) {
            return null;
        }
        if (userDetails.getActiveAuthToken() != null) {
            ResponseEntityWrapper.k_authTokenRepository.delete(userDetails.getActiveAuthToken());
        }
        return ResponseEntityWrapper.k_authTokenRepository.save(new AuthToken(ResponseEntityWrapper.k_tokenUtil.JWT_generate(userDetails.getUser().getUsername()), ResponseEntityWrapper.k_tokenUtil.RFT_generate(), userDetails.getUser()));
    }
}
