package com.kronsoft.pharma.auth.dto;

import com.kronsoft.pharma.city.CityDto;
import com.kronsoft.pharma.country.CountryDto;
import com.kronsoft.pharma.user.UserRepository;
import com.kronsoft.pharma.util.validator.equal_fields.EqualFields;
import com.kronsoft.pharma.util.validator.unique.Unique;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualFields(baseField = "password", matchField = "confirmPassword", message = "Passwords do not match")
public class RegisterDto {
    @NotNull
    @Size(min = 3, message = "Username should be at least 3 characters long")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$")
    @Unique(field = "username", repository = UserRepository.class, message = "Username must be unique")
    private String username;

    @NotNull
    @Size(min = 5, message = "Password should be at least 5 characters long")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])^.+$")
    private String password;

    @NotNull
    @Size(min = 5, message = "Confirm password should be at least 5 characters long")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])^.+$")
    private String confirmPassword;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String street;

    @NotNull
    @Valid
    private CityDto city;

    @NotNull
    @Valid
    private CountryDto country;

    @NotNull
    private Boolean acceptedTerms;

    private Set<String> roles;
}
