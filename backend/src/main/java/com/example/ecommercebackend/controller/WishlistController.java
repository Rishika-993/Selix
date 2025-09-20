package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.dto.MessageResponse;
import com.example.ecommercebackend.dto.WishlistDTO;
import com.example.ecommercebackend.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/{userId}")
    public List<WishlistDTO> getWishlist(@PathVariable Long userId) {
        return wishlistService.getWishlistByUser(userId);
    }

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<?> addProductToWishlist(@PathVariable Long userId, @PathVariable Long productId) {
        return ResponseEntity.ok(wishlistService.addProductToWishlist(userId, productId));
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<MessageResponse> removeProductFromWishlist(@PathVariable Long userId, @PathVariable Long productId) {
        wishlistService.removeProductFromWishlist(userId, productId);
        MessageResponse messageResponse = new MessageResponse("Removed successfully");
        return ResponseEntity.ok(messageResponse);
    }
}
