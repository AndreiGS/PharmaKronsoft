package com.kronsoft.pharma.user;

import com.kronsoft.pharma.user.dto.UserResponseDto;
import com.kronsoft.pharma.user.mapper.UserMapper;
import com.kronsoft.pharma.util.AuthenticationUtil;
import com.kronsoft.pharma.util.ResponseEntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public ResponseEntityWrapper<UserResponseDto> getUser() {
        return new ResponseEntityWrapper<>(userMapper.userToUserResponseDto(AuthenticationUtil.getUserDetails().getUser()), HttpStatus.OK);
    }

    public Boolean doesUsernameExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
