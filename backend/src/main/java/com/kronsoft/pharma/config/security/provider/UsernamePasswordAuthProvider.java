package com.kronsoft.pharma.config.security.provider;

import com.kronsoft.pharma.config.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthProvider implements AuthenticationProvider {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String providedUsername = authentication.getPrincipal().toString();
        UserDetails user = userDetailsService.loadUserByUsername(providedUsername);

        String providedPassword = authentication.getCredentials().toString();
        String correctPassword = user.getPassword();

        if(!providedPassword.equals(correctPassword)) {
            throw new RuntimeException("Incorrect Credentials");
        }

        return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
