package com.kronsoft.pharma.util;

import com.kronsoft.pharma.config.security.MyUserDetails;
import com.kronsoft.pharma.config.security.MyUserDetailsService;
import com.kronsoft.pharma.config.security.util.TokenUtil;
import com.kronsoft.pharma.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final TokenUtil tokenUtil;

    @Autowired
    public AuthenticationUtil(@Lazy AuthenticationManager authenticationManager, @Lazy MyUserDetailsService myUserDetailsService, @Lazy TokenUtil tokenUtil) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.tokenUtil = tokenUtil;
    }

    public static MyUserDetails getUserDetails() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void authenticate(AppUser user) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    public MyUserDetails authenticate(String jwt) {
        MyUserDetails userDetails = (MyUserDetails) myUserDetailsService.loadUserByUsername(tokenUtil.getUsername(jwt));
        AppUser user = userDetails.getUser();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return userDetails;
    }
}
