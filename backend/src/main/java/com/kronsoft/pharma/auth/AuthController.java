package com.kronsoft.pharma.auth;

import com.kronsoft.pharma.auth.dto.LoginDto;
import com.kronsoft.pharma.auth.dto.RegisterDto;
import com.kronsoft.pharma.user.dto.UserResponseDto;
import com.kronsoft.pharma.util.ResponseEntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntityWrapper<Void> register(@Valid @RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntityWrapper<Void> login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }
}
