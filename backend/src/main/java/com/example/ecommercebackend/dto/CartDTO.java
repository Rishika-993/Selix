package com.example.ecommercebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDTO {
    private Long cartId;
    private List<ProductDTO> products = new ArrayList<>();
    private Double totalPrice = 0.0;
}
