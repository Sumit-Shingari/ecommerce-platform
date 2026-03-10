package com.nagp.ecommerce_poc.service;

import com.nagp.ecommerce_poc.entity.*;
import com.nagp.ecommerce_poc.exceptions.OutOfStockException;
import com.nagp.ecommerce_poc.repository.*;
import com.nagp.ecommerce_poc.response.OrderItemResponse;
import com.nagp.ecommerce_poc.response.OrderResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductSkuRepository skuRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long placeOrder(String firebaseUid){

        User user = userRepository
                .findByFirebaseUid(firebaseUid)
                .orElseThrow();

        Cart cart = cartRepository
                .findByUser(user)
                .orElseThrow();

        if(cart.getItems().isEmpty()){
            throw new RuntimeException("Cart empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());

        double total = 0;

        for(CartItem cartItem : cart.getItems()){

            ProductSku sku = cartItem.getSku();

            if (sku.getStock() < cartItem.getQuantity()) {

                throw new OutOfStockException(
                        "Only " + sku.getStock() +
                                " items available for " +
                                sku.getProduct().getName()
                );
            }

            sku.setStock(
                    sku.getStock() - cartItem.getQuantity()
            );

            skuRepository.save(sku);

            OrderItem item = new OrderItem();

            item.setOrder(order);
            item.setSkuId(sku.getId());
            item.setProductName(sku.getProduct().getName());
            item.setBrand(sku.getProduct().getBrand());
            item.setSize(sku.getSize());
            item.setColor(sku.getColor());
            item.setPrice(sku.getPrice());
            item.setQuantity(cartItem.getQuantity());

            order.getItems().add(item);

            total += sku.getPrice() * cartItem.getQuantity();
        }

        order.setTotalAmount(total);

        orderRepository.save(order);

        // 🔥 CLEAR CART
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cartRepository.save(cart);

        return order.getId();
    }

    public List<OrderResponse> getOrders(String firebaseUid){

        User user = userRepository
                .findByFirebaseUid(firebaseUid)
                .orElseThrow();

        List<Order> orders =
                orderRepository.findByUserOrderByCreatedAtDesc(user);

        return orders.stream()
                .map(order -> OrderResponse.builder()

                        .orderId(order.getId())
                        .status(order.getStatus())
                        .totalAmount(order.getTotalAmount())
                        .createdAt(order.getCreatedAt())

                        .items(
                                order.getItems()
                                        .stream()
                                        .map(item -> OrderItemResponse.builder()

                                                .skuId(item.getSkuId())
                                                .productName(item.getProductName())
                                                .brand(item.getBrand())
                                                .size(item.getSize())
                                                .color(item.getColor())
                                                .price(item.getPrice())
                                                .quantity(item.getQuantity())

                                                .build())
                                        .toList()
                        )

                        .build()
                )
                .toList();
    }

}
