package com.kronsoft.pharma.security.util;

import com.kronsoft.pharma.PharmaApplication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class PathChecker {
    public static List<String> permitPaths = List.of(
        "/**/login", "/**/register",
        "/swagger.html", "/**/article",
        "/**/username_exists",
        "/**/fcm/**"
    );

    public boolean isPermitAllPath(HttpServletRequest request) {
        if (!PharmaApplication.HAS_AUTH) {
            return true;
        }

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        return permitPaths.stream().anyMatch((path) -> antPathMatcher.match(path, request.getRequestURI()));
    }
}
