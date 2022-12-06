package com.kronsoft.pharma.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageOf<T> {
    List<T> content;
    int totalPages;
    int totalElements;
}
