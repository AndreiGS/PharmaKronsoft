package com.kronsoft.pharma.config.security.provider;

import com.kronsoft.pharma.config.security.MyUserDetailsService;
import com.kronsoft.pharma.config.security.token.JWTAuthenticationToken;
import com.kronsoft.pharma.config.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthProvider implements AuthenticationProvider {
    private final MyUserDetailsService userDetailsService;
    private final TokenUtil tokenUtil;

    @Autowired
    public JWTAuthProvider(MyUserDetailsService userDetailsService, TokenUtil tokenUtil) {
        this.userDetailsService = userDetailsService;
        this.tokenUtil = tokenUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String providedJWT = authentication.getCredentials().toString();
        UserDetails user = userDetailsService.loadUserByUsername(tokenUtil.getUsername(providedJWT));

        return new JWTAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JWTAuthenticationToken.class);
    }
}
