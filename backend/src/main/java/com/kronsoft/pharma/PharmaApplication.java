package com.kronsoft.pharma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PharmaApplication {

    /**
     * Set by AUTH environment variable in order to disable the token validation steps
     */
    public static boolean HAS_AUTH = true;

    public static void main(String[] args) {
        HAS_AUTH = Boolean.parseBoolean(System.getenv("AUTH"));
        SpringApplication.run(PharmaApplication.class, args);
    }

}
