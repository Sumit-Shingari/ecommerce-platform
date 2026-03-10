package com.nagp.ecommerce_poc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy =   GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private String description;
    private String category;
    private String gender;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @OneToMany(mappedBy="product", fetch = FetchType.EAGER)
    private Set<ProductSku> skus;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductAttribute> attributes;
}
