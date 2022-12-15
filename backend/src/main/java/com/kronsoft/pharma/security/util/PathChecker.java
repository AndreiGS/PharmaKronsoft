package com.kronsoft.pharma.auth.util;

import com.kronsoft.pharma.PharmaApplication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class PathChecker {
    public static List<String> permitPaths = List.of(
        "/auth/login", "/auth/register",
        "/swagger.html", "/article",
        "/user/username_exists",
        "/fcm/notification", "fcm/topic/**"
    );

    public boolean isPermitAllPath(HttpServletRequest request) {
        if (!PharmaApplication.HAS_AUTH) {
            return true;
        }

        return permitPaths.stream().anyMatch((path) -> request.getRequestURI().contains(path));
    }
}
