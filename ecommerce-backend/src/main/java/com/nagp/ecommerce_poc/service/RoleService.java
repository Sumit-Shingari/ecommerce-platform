package com.nagp.ecommerce_poc.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoleService {

    public void assignRole(String uid, String role)
            throws FirebaseAuthException {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        FirebaseAuth.getInstance()
                .setCustomUserClaims(uid, claims);
    }
}
