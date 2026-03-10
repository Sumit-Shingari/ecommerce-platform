package com.nagp.ecommerce_poc.controller;

import com.nagp.ecommerce_poc.model.FirebaseUserPrincipal;
import com.nagp.ecommerce_poc.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(Authentication auth){

        FirebaseUserPrincipal user =
                (FirebaseUserPrincipal) auth.getPrincipal();

        Long orderId =
                orderService.placeOrder(user.uid());

        return ResponseEntity.ok(
                Map.of("orderId",orderId)
        );
    }

    @GetMapping
    public ResponseEntity<?> getOrders(Authentication auth){

        FirebaseUserPrincipal user =
                (FirebaseUserPrincipal) auth.getPrincipal();

        return ResponseEntity.ok(
                orderService.getOrders(user.uid())
        );
    }
}
