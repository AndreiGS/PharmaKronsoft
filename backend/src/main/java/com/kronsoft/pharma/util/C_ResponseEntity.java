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
import org.springframework.util.MultiValueMapAdapter;

import javax.annotation.PostConstruct;

@Component
public class C_ResponseEntity<T> extends ResponseEntity<T> {
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
        C_ResponseEntity.k_authTokenRepository = this.authTokenRepository;
        C_ResponseEntity.k_tokenUtil = this.tokenUtil;
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
        return C_ResponseEntity.build(body, headers, status);
    }

    public C_ResponseEntity() {
        this(null, HttpStatus.OK);
    }

    public C_ResponseEntity(T body, HttpStatus status) {
        this(build(body, status));
    }

    public C_ResponseEntity(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        this(build(body, headers, status));
    }

    private C_ResponseEntity(ResponseEntity<T> responseEntity) {
        super(responseEntity.getBody(), responseEntity.getHeaders(), responseEntity.getStatusCode());
    }

    private static AuthToken init() {
        if (C_ResponseEntity.k_authTokenRepository == null || C_ResponseEntity.k_tokenUtil == null) {
            return null;
        }

        MyUserDetails userDetails = AuthenticationUtil.getUserDetails();
        if (userDetails == null) {
            return null;
        }
        if (userDetails.getActiveAuthToken() != null) {
            C_ResponseEntity.k_authTokenRepository.delete(userDetails.getActiveAuthToken());
        }
        return C_ResponseEntity.k_authTokenRepository.save(new AuthToken(C_ResponseEntity.k_tokenUtil.JWT_generate(userDetails.getUser().getUsername()), C_ResponseEntity.k_tokenUtil.RFT_generate(), userDetails.getUser()));
    }
}
