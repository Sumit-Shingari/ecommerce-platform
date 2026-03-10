package com.nagp.ecommerce_poc.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;

    private String status;

    private Double totalAmount;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;

}
