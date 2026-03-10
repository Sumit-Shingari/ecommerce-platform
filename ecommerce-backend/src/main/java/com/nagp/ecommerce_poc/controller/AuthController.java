package com.nagp.ecommerce_poc.controller;

import com.nagp.ecommerce_poc.model.FirebaseUserPrincipal;
import com.nagp.ecommerce_poc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/sync")
    public ResponseEntity<?> syncUser(Authentication authentication) {

        FirebaseUserPrincipal principal =
                (FirebaseUserPrincipal) authentication.getPrincipal();

        String firebaseUid = principal.uid();
        String email = principal.email();

        userService.syncUser(firebaseUid, email);

        return ResponseEntity.ok("User synced");
    }
}