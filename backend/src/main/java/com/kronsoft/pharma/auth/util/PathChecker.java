package com.kronsoft.pharma.auth.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class PathChecker {
    public boolean isPermitAllPath(HttpServletRequest request) {
        List<String> permitPaths = List.of("/login", "/register", "/swagger.html", "/article");
        return permitPaths.stream().anyMatch((path) -> request.getRequestURI().contains(path));
    }
}
