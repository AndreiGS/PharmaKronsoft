package com.kronsoft.pharma.auth;

import com.kronsoft.pharma.auth.dto.LoginDto;
import com.kronsoft.pharma.auth.dto.RegisterDto;
import com.kronsoft.pharma.auth.exception.InvalidRoleException;
import com.kronsoft.pharma.auth.role.ERole;
import com.kronsoft.pharma.auth.role.Role;
import com.kronsoft.pharma.auth.role.RoleRepository;
import com.kronsoft.pharma.auth.role.constants.RoleConstants;
import com.kronsoft.pharma.city.City;
import com.kronsoft.pharma.city.CityDto;
import com.kronsoft.pharma.city.CityRepository;
import com.kronsoft.pharma.country.Country;
import com.kronsoft.pharma.country.CountryDto;
import com.kronsoft.pharma.country.CountryRepository;
import com.kronsoft.pharma.user.AppUser;
import com.kronsoft.pharma.user.UserRepository;
import com.kronsoft.pharma.util.AuthenticationUtil;
import com.kronsoft.pharma.util.BaseMapper;
import com.kronsoft.pharma.util.ResponseEntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final AuthTokenRepository authTokenRepository;
    private final BaseMapper baseMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationUtil authenticationUtil;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, CountryRepository countryRepository, CityRepository cityRepository, AuthTokenRepository authTokenRepository, BaseMapper baseMapper, PasswordEncoder encoder, AuthenticationUtil authenticationUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.authTokenRepository = authTokenRepository;
        this.baseMapper = baseMapper;
        this.encoder = encoder;
        this.authenticationUtil = authenticationUtil;
    }

    public ResponseEntityWrapper<Void> register(RegisterDto registerDto) {
        AppUser user = baseMapper.dtoToEntity(registerDto, AppUser.class);
        user.setRoles(getRoles(registerDto));
        user.setPassword(encoder.encode(registerDto.getPassword()));
        Country country = findCountry(registerDto.getCountry());
        user.setCity(findCity(registerDto.getCity(), country));
        userRepository.save(user);

        AppUser baseMappedUser = baseMapper.dtoToEntity(registerDto, AppUser.class);
        authenticationUtil.authenticate(baseMappedUser);

        return new ResponseEntityWrapper<>(HttpStatus.CREATED);
    }

    public ResponseEntityWrapper<Void> login(LoginDto loginDto) {
        AppUser tryingToLog = baseMapper.dtoToEntity(loginDto, AppUser.class);
        authenticationUtil.authenticate(tryingToLog);
        return new ResponseEntityWrapper<>(HttpStatus.OK);
    }

    public void logout() {
        authTokenRepository.delete(AuthenticationUtil.getUserDetails().getActiveAuthToken());
    }

    private Set<Role> getRoles(RegisterDto registerDto) {
        Set<String> strRoles = registerDto.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(InvalidRoleException::new);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case RoleConstants.ADMIN:
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(InvalidRoleException::new);
                        roles.add(adminRole);
                        break;
                    case RoleConstants.MODERATOR:
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(InvalidRoleException::new);
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(InvalidRoleException::new);
                        roles.add(userRole);
                        break;
                }
            });
        }

        return roles;
    }

    private Country findCountry(CountryDto countryDto) {
        return countryRepository.findById(countryDto.getId())
                                .orElseGet(() -> countryRepository.save(baseMapper.dtoToEntity(countryDto, Country.class)));
    }

    private City findCity(CityDto cityDto, Country country) {
        Optional<City> cityOptional = cityRepository.findById(cityDto.getId());

        if(cityOptional.isPresent()) {
            return cityOptional.get();
        }
        City city = baseMapper.dtoToEntity(cityDto, City.class);
        city.setCountry(country);
        return cityRepository.save(city);
    }
}