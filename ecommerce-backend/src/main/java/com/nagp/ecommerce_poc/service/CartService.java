package com.nagp.ecommerce_poc.service;

import com.nagp.ecommerce_poc.entity.*;
import com.nagp.ecommerce_poc.repository.CartItemRepository;
import com.nagp.ecommerce_poc.repository.CartRepository;
import com.nagp.ecommerce_poc.repository.ProductSkuRepository;
import com.nagp.ecommerce_poc.repository.UserRepository;
import com.nagp.ecommerce_poc.response.CartItemResponse;
import com.nagp.ecommerce_poc.response.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductSkuRepository skuRepository;
    private final UserRepository userRepository;

    public void addToCart(String firebaseUid, Long skuId, int quantity){

        User user = userRepository
                .findByFirebaseUid(firebaseUid)
                .orElseThrow();

        Cart cart = cartRepository
                .findByUser(user)
                .orElseGet(() -> {

                    Cart c = new Cart();
                    c.setUser(user);
                    return cartRepository.save(c);

                });

        ProductSku sku = skuRepository
                .findById(skuId)
                .orElseThrow();

        Optional<CartItem> existing =
                cartItemRepository.findByCartAndSku(cart, sku);

        if(existing.isPresent()){

            CartItem item = existing.get();
            item.setQuantity(item.getQuantity()+quantity);
            cartItemRepository.save(item);

        } else {

            CartItem item = new CartItem();
            item.setCart(cart);
            item.setSku(sku);
            item.setQuantity(quantity);

            cartItemRepository.save(item);
        }
    }

    public int getCartCount(String firebaseUid){

        User user = userRepository
                .findByFirebaseUid(firebaseUid)
                .orElseThrow();

        Cart cart = cartRepository
                .findByUser(user)
                .orElse(null);

        if(cart == null) return 0;

        return cart.getItems()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

        public CartResponse getCart(String firebaseUid) {

            User user = userRepository
                    .findByFirebaseUid(firebaseUid)
                    .orElseThrow();

            Cart cart = cartRepository
                    .findByUser(user)
                    .orElse(null);

            if (cart == null) {

                return CartResponse.builder()
                        .items(List.of())
                        .subtotal(0.0)
                        .build();
            }

            List<CartItemResponse> items = new ArrayList<>();

            double subtotal = 0;

            for (CartItem item : cart.getItems()) {

                ProductSku sku = item.getSku();
                Product product = sku.getProduct();

                double itemTotal = sku.getPrice() * item.getQuantity();

                subtotal += itemTotal;

                items.add(
                        CartItemResponse.builder()
                                .itemId(item.getId())
                                .skuId(sku.getId())
                                .productId(product.getId())
                                .productName(product.getName())
                                .brand(product.getBrand())
                                .size(sku.getSize())
                                .color(sku.getColor())
                                .imageUrl(sku.getImageUrl())
                                .price(sku.getPrice())
                                .quantity(item.getQuantity())
                                .build()
                );
            }

            return CartResponse.builder()
                    .items(items)
                    .subtotal(subtotal)
                    .build();
        }

        public void updateQuantity(Long itemId, int quantity) {

            CartItem item = cartItemRepository
                    .findById(itemId)
                    .orElseThrow();

            item.setQuantity(quantity);

            cartItemRepository.save(item);
        }

        public void removeItem(Long itemId) {

            cartItemRepository.deleteById(itemId);
        }
}
