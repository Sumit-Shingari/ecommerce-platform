package com.nagp.ecommerce_poc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    private Long skuId;

    private String productName;

    private String brand;

    private String size;

    private String color;

    private Double price;

    private Integer quantity;
}
