package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductName(String productName);

    //    List<Product> findByCategory(Long categoryId);
    Page<Product> findByCategory_CategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByCategory_CategoryName(String categoryName, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByProductNameLikeIgnoreCase(String name, Pageable pageable);
}
