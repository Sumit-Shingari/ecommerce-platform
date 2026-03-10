package com.nagp.ecommerce_poc.repository;

import com.nagp.ecommerce_poc.entity.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {
}
