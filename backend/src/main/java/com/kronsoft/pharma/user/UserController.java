package com.kronsoft.pharma.user;

import com.kronsoft.pharma.user.dto.UserResponseDto;
import com.kronsoft.pharma.util.C_ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    private C_ResponseEntity<UserResponseDto> getUser() {
        return userService.getUser();
    }
}
