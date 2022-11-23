package com.kronsoft.pharma.user;

import com.kronsoft.pharma.user.dto.UserResponseDto;
import com.kronsoft.pharma.user.mapper.UserMapper;
import com.kronsoft.pharma.util.AuthenticationUtil;
import com.kronsoft.pharma.util.C_ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public C_ResponseEntity<UserResponseDto> getUser() {
        return new C_ResponseEntity<>(userMapper.userToUserResponseDto(AuthenticationUtil.getUserDetails().getUser()), HttpStatus.OK);
    }
}
