package com.nagp.ecommerce_poc.repository;

import com.nagp.ecommerce_poc.entity.Cart;
import com.nagp.ecommerce_poc.entity.CartItem;
import com.nagp.ecommerce_poc.entity.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    Optional<CartItem> findByCartAndSku(Cart cart, ProductSku sku);

}
