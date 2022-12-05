package com.kronsoft.pharma.user.mapper;

import com.kronsoft.pharma.auth.dto.LoginDto;
import com.kronsoft.pharma.auth.dto.RegisterDto;
import com.kronsoft.pharma.user.AppUser;
import com.kronsoft.pharma.user.dto.UserResponseDto;
import com.kronsoft.pharma.util.BaseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends BaseMapper {
    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    public<T> AppUser dtoToUser(T dto) {
        return super.dtoToEntity(dto, AppUser.class);
    }

    public UserResponseDto userToUserResponseDto(AppUser user) {
        return super.entityToDto(user, UserResponseDto.class);
    }
}
