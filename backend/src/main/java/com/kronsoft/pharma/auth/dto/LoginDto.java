package com.kronsoft.pharma.auth.dto;

import com.kronsoft.pharma.user.UserRepository;
import com.kronsoft.pharma.util.validator.unique.Unique;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {
    @NotNull
    @Size(min = 3, message = "Username should be at least 3 characters long")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$")
    private String username;

    @NotNull
    @Size(min = 5, message = "Password should be at least 5 characters long")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])^.+$")
    private String password;
}
