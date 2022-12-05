package com.kronsoft.pharma.config.security.util;

import com.kronsoft.pharma.config.security.MyUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

/**
 * Utility class that provides token manipulation methods (creation and validation methods)
 */
@Component
public class TokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);
    private final String jwtSecret;
    private final String rftSecret;
    private final Long jwtExpirationMs;
    private final Long rftExpirationMs;
    private final SecretKey JWT_SigningKey;
    private final SecretKey RFT_SigningKey;

    public TokenUtil(@Value("${pharma.app.jwtSecret}") String jwtSecret,
                     @Value("${pharma.app.jwtExpirationMs}") Long jwtExpirationMs,
                     @Value("${pharma.app.rftSecret}") String rftSecret,
                     @Value("${pharma.app.rftExpirationMs}") Long rftExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
        this.rftSecret = rftSecret;
        this.rftExpirationMs = rftExpirationMs;
        JWT_SigningKey = getSigningKey(jwtSecret);
        RFT_SigningKey = getSigningKey(rftSecret);
    }

    private SecretKey getSigningKey(String secret) {
        if (secret == null) return null;

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String JWT_generate(Authentication authentication) {
        MyUserDetails userPrincipal = (MyUserDetails) authentication.getPrincipal();
        return JWT_generate(userPrincipal.getUsername());
    }

    public String JWT_generate(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(JWT_SigningKey)
                .compact();
    }

    public String RFT_generate() {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + rftExpirationMs))
                .signWith(RFT_SigningKey)
                .compact();
    }


    /**
     * @param token JWT authentication token
     * @return username extracted from the JWT token used
     */
    public String getUsername(String token) {
        if (token.startsWith("Bearer")) {
            token = token.substring(7);
        }
        return Jwts.parserBuilder().setSigningKey(JWT_SigningKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * @param key   the signing key used to encrypt the token
     * @param token the token that needs validation
     * @return the validity state of the token
     */
    private boolean isValid(Key key, String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("JWT claims failed: {}", e.getMessage());
        }

        return false;
    }

    public boolean JWT_isValid(String token) {
        return isValid(JWT_SigningKey, token);
    }

    public boolean RFT_isValid(String token) {
        return isValid(RFT_SigningKey, token);
    }

    /**
     * @param request the request provided by rest application endpoind
     * @return jwt token extracted from header
     */
    public String getJWTFromHeader(HttpServletRequest request) {
        return request.getHeader(TokenConstants.JWT_HEADER);
    }


    /**
     * @param request the request provided by rest application endpoind
     * @return jwt token extracted from header
     */
    public String getRefreshFromHeader(HttpServletRequest request) {
        return request.getHeader(TokenConstants.REFRESH_HEADER);
    }
}
