package com.nagp.ecommerce_poc.controller;

import com.nagp.ecommerce_poc.model.FirebaseUserPrincipal;
import com.nagp.ecommerce_poc.request.AddToCartRequest;
import com.nagp.ecommerce_poc.request.UpdateQuantityRequest;
import com.nagp.ecommerce_poc.response.CartResponse;
import com.nagp.ecommerce_poc.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            Authentication authentication,
            @RequestBody AddToCartRequest request
    ){

        FirebaseUserPrincipal user =
                (FirebaseUserPrincipal) authentication.getPrincipal();

        cartService.addToCart(
                user.uid(),
                request.getSkuId(),
                request.getQuantity()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCartCount(Authentication authentication) {

        FirebaseUserPrincipal user =
                (FirebaseUserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(
                cartService.getCartCount(user.uid())
        );
    }

        @GetMapping
        public ResponseEntity<CartResponse> getCart(Authentication authentication) {

            FirebaseUserPrincipal user =
                    (FirebaseUserPrincipal) authentication.getPrincipal();

            return ResponseEntity.ok(
                    cartService.getCart(user.uid())
            );
        }

        @PutMapping("/update")
        public ResponseEntity<?> updateQuantity(
                @RequestBody UpdateQuantityRequest request) {

            cartService.updateQuantity(
                    request.getItemId(),
                    request.getQuantity()
            );

            return ResponseEntity.ok().build();
        }

        @DeleteMapping("/{itemId}")
        public ResponseEntity<?> removeItem(
                @PathVariable Long itemId) {

            cartService.removeItem(itemId);

            return ResponseEntity.ok().build();
        }
}
