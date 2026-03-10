package com.nagp.ecommerce_poc.repository;

import com.nagp.ecommerce_poc.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"skus","attributes"})
    Optional<Product> findWithDetailsById(Long id);
}
