package com.kronsoft.pharma.user.mapper;

import com.kronsoft.pharma.auth.dto.RegisterDto;
import com.kronsoft.pharma.user.AppUser;
import com.kronsoft.pharma.user.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AppUser registerToUser(RegisterDto registerDto) {
        return this.modelMapper.map(registerDto, AppUser.class);
    }
    public UserResponseDto userToUserResponseDto(AppUser user) { return this.modelMapper.map(user, UserResponseDto.class); }
}
