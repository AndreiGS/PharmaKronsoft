package com.kronsoft.pharma.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseWrapper<T> {
    T body;
    String jwt;
    String refreshToken;
}
