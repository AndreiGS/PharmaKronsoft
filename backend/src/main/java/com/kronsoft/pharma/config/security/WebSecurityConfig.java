package com.kronsoft.pharma.config.security;

import com.kronsoft.pharma.config.security.filter.JWTFilter;
import com.kronsoft.pharma.config.security.filter.RefreshTokenFilter;
import com.kronsoft.pharma.config.security.provider.JWTAuthProvider;
import com.kronsoft.pharma.config.security.provider.UsernamePasswordAuthProvider;
import com.kronsoft.pharma.config.security.util.TokenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
    private final RefreshTokenFilter refreshTokenFilter;
    private final JWTFilter jwtFilter;
    private final MyUserDetailsService myUserDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTAuthProvider jwtAuthProvider;
    private final UsernamePasswordAuthProvider usernamePasswordAuthProvider;

    @Autowired
    public WebSecurityConfig(RefreshTokenFilter refreshTokenFilter, JWTFilter jwtFilter, MyUserDetailsService myUserDetailsService, JWTAuthProvider jwtAuthProvider, UsernamePasswordAuthProvider usernamePasswordAuthProvider) {
        this.refreshTokenFilter = refreshTokenFilter;
        this.jwtFilter = jwtFilter;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtAuthProvider = jwtAuthProvider;
        this.usernamePasswordAuthProvider = usernamePasswordAuthProvider;
    }

    /**
     * Sets the endpoints being reached conditions based on authentication filters and authorization status of endpoints (needs authenticated or permits all requests)
     * @param http security provided by spring
     * @param authManager authentication manager provided by spring
     * @return the security chain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        http
            .cors()
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests().antMatchers("/**/login").permitAll()
            .and()
            .authorizeRequests().antMatchers("/**/register").permitAll()
            .and()
            .authorizeRequests().antMatchers("/**").authenticated()
            .and()
            .httpBasic().disable()
            .addFilterAfter(refreshTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authenticationManager(authManager);
        return http.build();
    }

    /**
     * Class used by the spring context to save the current authenticated user (the user whom request accessed the endpoint)
     * @return custom user implementation
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return myUserDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        if (bCryptPasswordEncoder == null) {
            bCryptPasswordEncoder = new BCryptPasswordEncoder();
        }
        return bCryptPasswordEncoder;
    }

    /**
     * Sets the authentication details used in the configuration (password encoder, authentication providers and the UserDetailsService whose job is to load the current user)
     * Authentication providers are custom providers and implements the 'supports' method to check whether their authentication method has been called
     * @param http security provided by spring
     * @return the AuthencationManager used in the filter chain and responsible for authentication of the user (via authentication providers)
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(myUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder())
                .and()
                .authenticationProvider(jwtAuthProvider)
                .authenticationProvider(usernamePasswordAuthProvider)
                .build();
    }

    /**
     * Method that sets the cross origin configuration settings
     * @return cors configuration
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        List<String> frontendUrls = new ArrayList<>();
        frontendUrls.add("http://localhost:4200");

        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setExposedHeaders(Arrays.asList(TokenConstants.JWT_HEADER, TokenConstants.REFRESH_HEADER));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
