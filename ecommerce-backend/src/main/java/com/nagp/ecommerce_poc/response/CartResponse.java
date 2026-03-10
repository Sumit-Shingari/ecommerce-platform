package com.nagp.ecommerce_poc.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartResponse {

    private List<CartItemResponse> items;

    private Double subtotal;

}
