package com.kronsoft.pharma.auth;

import com.kronsoft.pharma.auth.dto.RegisterDto;
import com.kronsoft.pharma.auth.role.ERole;
import com.kronsoft.pharma.user.dto.UserResponseDto;
import com.kronsoft.pharma.user.mapper.UserMapper;
import com.kronsoft.pharma.auth.role.Role;
import com.kronsoft.pharma.auth.role.RoleRepository;
import com.kronsoft.pharma.auth.role.exeption.NotRoleException;
import com.kronsoft.pharma.user.AppUser;
import com.kronsoft.pharma.user.UserRepository;
import com.kronsoft.pharma.util.AuthenticationUtil;
import com.kronsoft.pharma.util.ResponseEntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kronsoft.pharma.auth.role.constants.RoleConstants;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationUtil authenticationUtil;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder encoder, AuthenticationUtil authenticationUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
        this.authenticationUtil = authenticationUtil;
    }

    public ResponseEntityWrapper<UserResponseDto> register(RegisterDto registerDto) {
        AppUser user = userMapper.registerToUser(registerDto);
        user.setRoles(getRoles(registerDto));
        user.setPassword(encoder.encode(registerDto.getPassword()));
        AppUser newUser = userRepository.save(user);
        authenticationUtil.authenticate(newUser);

        return new ResponseEntityWrapper<>(userMapper.userToUserResponseDto(newUser), HttpStatus.CREATED);
    }

    private Set<Role> getRoles(RegisterDto registerDto) {
        Set<String> strRoles = registerDto.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(NotRoleException::new);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case RoleConstants.ADMIN:
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(NotRoleException::new);
                        roles.add(adminRole);
                        break;
                    case RoleConstants.MODERATOR:
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(NotRoleException::new);
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(NotRoleException::new);
                        roles.add(userRole);
                        break;
                }
            });
        }

        return roles;
    }
}
