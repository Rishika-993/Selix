package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.WishlistDTO;

import java.util.List;

public interface WishlistService {
    List<WishlistDTO> getWishlistByUser(Long userId);
    WishlistDTO addProductToWishlist(Long userId, Long productId);
    void removeProductFromWishlist(Long userId, Long productId);
}

