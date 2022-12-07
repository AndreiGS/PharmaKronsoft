package com.kronsoft.pharma.user;

import com.kronsoft.pharma.user.dto.UserResponseDto;
import com.kronsoft.pharma.util.AuthenticationUtil;
import com.kronsoft.pharma.util.BaseMapper;
import com.kronsoft.pharma.util.ResponseEntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final BaseMapper baseMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(BaseMapper baseMapper, UserRepository userRepository) {
        this.baseMapper = baseMapper;
        this.userRepository = userRepository;
    }

    public ResponseEntityWrapper<UserResponseDto> getUser() {
        return new ResponseEntityWrapper<>(baseMapper.entityToDto(AuthenticationUtil.getUserDetails().getUser(), UserResponseDto.class), HttpStatus.OK);
    }

    public Boolean doesUsernameExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
