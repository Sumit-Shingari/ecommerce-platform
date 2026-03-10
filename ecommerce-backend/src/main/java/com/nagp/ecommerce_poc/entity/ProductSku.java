package com.nagp.ecommerce_poc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="product_skus")
@Getter
@Setter
public class ProductSku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skuCode;
    private String size;
    private String color;
    private Double price;
    private Integer stock;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}
