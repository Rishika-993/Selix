package com.example.ecommercebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemDTO {
    private Long cartItemId;
    private CartDTO cart;
    private ProductDTO products;
    private Integer quantity;
    private Double discount;
    private Double productPrice;
}
