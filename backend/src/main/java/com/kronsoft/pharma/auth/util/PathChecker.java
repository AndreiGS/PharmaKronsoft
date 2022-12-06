package com.kronsoft.pharma.auth.util;

import com.kronsoft.pharma.PharmaApplication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class PathChecker {
    public boolean isPermitAllPath(HttpServletRequest request) {
        if (!PharmaApplication.HAS_AUTH) {
            return true;
        }

        List<String> permitPaths = List.of("/login", "/register", "/username_exists", "/swagger.html");
        return permitPaths.stream().anyMatch((path) -> request.getRequestURI().contains(path));
    }
}
