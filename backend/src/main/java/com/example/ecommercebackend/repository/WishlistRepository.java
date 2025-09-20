package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query("SELECT w FROM Wishlist w WHERE w.user.userId = :userId")
    List<Wishlist> findByUserId(@Param("userId") Long userId);

    @Query("SELECT w FROM Wishlist w WHERE w.user.userId = :userId AND w.product.productId = :productId")
    Optional<Wishlist> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}

