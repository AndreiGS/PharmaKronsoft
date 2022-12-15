package com.kronsoft.pharma.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kronsoft.pharma.auth.AuthToken;
import com.kronsoft.pharma.auth.AuthTokenRepository;
import com.kronsoft.pharma.security.MyUserDetails;
import com.kronsoft.pharma.security.util.TokenConstants;
import com.kronsoft.pharma.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;


/**
 * Class to use whenever the user has to get another pair of JWT and RefreshToken
 * Automatization of token creation
 *
 * @param <T> the type of the response body
 */
@Component
public class ResponseEntityWrapper<T> extends ResponseEntity<T> {
    @JsonIgnore
    private static AuthTokenRepository k_authTokenRepository;
    @JsonIgnore
    private static TokenUtil k_tokenUtil;
    @JsonIgnore
    @Autowired
    private AuthTokenRepository authTokenRepository;
    @JsonIgnore
    @Autowired
    private TokenUtil tokenUtil;

    public ResponseEntityWrapper() {
        this(null, HttpStatus.OK);
    }

    public ResponseEntityWrapper(T body, HttpStatus status) {
        this(build(body, status));
    }

    public ResponseEntityWrapper(HttpStatus status) {
        this(build(null, status));
    }

    public ResponseEntityWrapper(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        this(build(body, headers, status));
    }

    private ResponseEntityWrapper(ResponseEntity<T> responseEntity) {
        super(responseEntity.getBody(), responseEntity.getHeaders(), responseEntity.getStatusCode());
    }

    /**
     * Used to build the response for the specific request
     *
     * @param body    template parameter used as response body
     * @param headers map of headers to send as response
     * @param status  status of current request
     * @return response entity for current request with the new tokens inserted into the map already provided
     */
    private static <T> ResponseEntity<T> build(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        AuthToken authToken = init();
        if (authToken == null) {
            return new ResponseEntity<>(body, headers, status);
        }
        headers.add(TokenConstants.JWT_HEADER, authToken.getJwtToken());
        headers.add(TokenConstants.REFRESH_HEADER, authToken.getRefreshToken());
        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * @param body   template parameter used as response body
     * @param status status of current request
     * @return response entity for current request with the new tokens inserted into an empty MultiValueMap used for headers
     */
    private static <T> ResponseEntity<T> build(T body, HttpStatus status) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        return ResponseEntityWrapper.build(body, headers, status);
    }

    /**
     * This method is responsible for recreating the authentication tokens and deleting the used ones
     *
     * @return new user authentication token
     */
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


    /**
     * Initializes the beans constructed during the startup into the static repository members
     * Static members are needed in order to use the build method and used in automating the recreation of auth tokens
     */
    @PostConstruct
    private void initializeBeans() {
        ResponseEntityWrapper.k_authTokenRepository = this.authTokenRepository;
        ResponseEntityWrapper.k_tokenUtil = this.tokenUtil;
    }
}
