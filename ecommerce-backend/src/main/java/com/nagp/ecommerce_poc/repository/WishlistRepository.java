package com.nagp.ecommerce_poc.repository;

import com.nagp.ecommerce_poc.entity.Product;
import com.nagp.ecommerce_poc.entity.User;
import com.nagp.ecommerce_poc.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);

    Optional<Wishlist> findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    long countByUser(User user);
}
