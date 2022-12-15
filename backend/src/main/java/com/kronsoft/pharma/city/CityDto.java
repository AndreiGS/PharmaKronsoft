package com.kronsoft.pharma.city;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityDto {
    @NotNull
    private Integer id;

    @NotNull
    @JsonProperty(value = "country_id")
    private Integer countryId;

    @NotNull
    private String name;
}
