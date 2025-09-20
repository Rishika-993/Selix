package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.ProductDTO;
import com.example.ecommercebackend.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductResponse fetchProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO createProduct(@Valid ProductDTO productDTO, Long categoryId);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);

    ProductResponse fetchProductsByCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, Long categoryId);

    ProductResponse searchProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword);

    ProductDTO updateProductImage(Long productId, MultipartFile productImage);
}
