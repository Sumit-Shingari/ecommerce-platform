package com.nagp.ecommerce_poc.service;

import com.nagp.ecommerce_poc.entity.Product;
import com.nagp.ecommerce_poc.entity.ProductSku;
import com.nagp.ecommerce_poc.entity.User;
import com.nagp.ecommerce_poc.entity.Wishlist;
import com.nagp.ecommerce_poc.repository.ProductRepository;
import com.nagp.ecommerce_poc.repository.UserRepository;
import com.nagp.ecommerce_poc.repository.WishlistRepository;
import com.nagp.ecommerce_poc.response.WishlistProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public void toggleWishlist(String firebaseUid, Long productId) {

        User user = userRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<Wishlist> existing =
                wishlistRepository.findByUserAndProduct(user, product);

        if (existing.isPresent()) {
            wishlistRepository.delete(existing.get());
        } else {
            Wishlist wishlist = new Wishlist();
            wishlist.setUser(user);
            wishlist.setProduct(product);
            wishlistRepository.save(wishlist);
        }
    }

    public List<WishlistProductResponse> getWishlist(String firebaseUid) {

        User user = userRepository
                .findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Wishlist> wishlist = wishlistRepository.findByUser(user);

        return wishlist.stream()
                .map(w -> {

                    Product product = w.getProduct();

                    ProductSku sku = product.getSkus()
                            .stream()
                            .min(Comparator.comparing(ProductSku::getPrice))
                            .orElseThrow();

                    return WishlistProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .brand(product.getBrand())
                            .imageUrl(product.getThumbnailUrl())
                            .price(sku.getPrice())
                            .build();
                })
                .toList();
    }

    public long getWishlistCount(String firebaseUid) {
        User user = userRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return wishlistRepository.countByUser(user);
    }
}
