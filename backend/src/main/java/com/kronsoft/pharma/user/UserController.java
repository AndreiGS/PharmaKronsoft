package com.kronsoft.pharma.user;

import com.kronsoft.pharma.config.security.util.IsAdmin;
import com.kronsoft.pharma.user.dto.UserResponseDto;
import com.kronsoft.pharma.util.ResponseEntityWrapper;
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
    public ResponseEntityWrapper<UserResponseDto> getUser() {
        return userService.getUser();
    }

    @GetMapping("/admin")
    @IsAdmin
    public String getAdmin() {
        return "admin";
    }
}
