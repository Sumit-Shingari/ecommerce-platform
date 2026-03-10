package com.nagp.ecommerce_poc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagp.ecommerce_poc.model.FirebaseUserPrincipal;
import com.nagp.ecommerce_poc.repository.WishlistRepository;
import com.nagp.ecommerce_poc.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/{productId}")
    public ResponseEntity<?> toggleWishlist(
            @PathVariable Long productId,
            Authentication authentication) {

        FirebaseUserPrincipal principal =
                (FirebaseUserPrincipal) authentication.getPrincipal();

        String firebaseUid = principal.uid();

        wishlistService.toggleWishlist(firebaseUid, productId);

        return ResponseEntity.ok("Wishlist updated");
    }

    @GetMapping
    public ResponseEntity<?> getWishlist(Authentication authentication) throws JsonProcessingException {

        FirebaseUserPrincipal principal =
                (FirebaseUserPrincipal) authentication.getPrincipal();

        String firebaseUid = principal.uid();

        return ResponseEntity.ok(Map.of(
                "products", wishlistService.getWishlist(firebaseUid)
        ));
    }

    @GetMapping("/count")
    public ResponseEntity<?> getWishlistCount(Authentication authentication) {

        FirebaseUserPrincipal principal =
                (FirebaseUserPrincipal) authentication.getPrincipal();

        String firebaseUid = principal.uid();

        return ResponseEntity.ok(
                wishlistService.getWishlistCount(firebaseUid)
        );
    }
}
