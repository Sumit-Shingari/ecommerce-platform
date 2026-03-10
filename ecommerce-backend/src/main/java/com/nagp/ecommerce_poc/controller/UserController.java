package com.nagp.ecommerce_poc.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/public/health")
    public String health() {
        return "Service is running";
    }

    @GetMapping("/user/profile")
    public String profile(Authentication authentication) {
        return "User UID: " + authentication.getName();
    }
}
