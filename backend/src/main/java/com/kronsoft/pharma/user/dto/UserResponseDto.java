package com.kronsoft.pharma.user.dto;

import com.kronsoft.pharma.city.CityDto;
import com.kronsoft.pharma.country.CountryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private String street;
    private CityDto city;
    private CountryDto country;
}
