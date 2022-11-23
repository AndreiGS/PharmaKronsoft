package com.kronsoft.pharma.auth;

import com.kronsoft.pharma.auth.dto.RegisterDto;
import com.kronsoft.pharma.user.AppUser;
import com.kronsoft.pharma.user.dto.UserResponseDto;
import com.kronsoft.pharma.util.C_ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    private C_ResponseEntity<UserResponseDto> register(@RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }
}
