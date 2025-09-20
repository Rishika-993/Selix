package com.example.ecommercebackend.service.impl;

import com.example.ecommercebackend.dto.WishlistDTO;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.model.User;
import com.example.ecommercebackend.model.Wishlist;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.repository.WishlistRepository;
import com.example.ecommercebackend.service.WishlistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    public List<WishlistDTO> getWishlistByUser(Long userId) {
        List<Wishlist> byUserId = wishlistRepository.findByUserId(userId);
//        List<WishlistDTO> wishlistDTOS = byUserId.stream().map(wishlist -> modelMapper.map(wishlist, WishlistDTO.class)).toList();
        List<WishlistDTO> wishlistDTOS = byUserId.stream().map(wishlist -> {
            WishlistDTO wishlistDTO = new WishlistDTO();
            wishlistDTO.setId(wishlist.getId());
            wishlistDTO.setProductName(wishlist.getProduct().getProductName());
            wishlistDTO.setProductPrice(wishlist.getProduct().getPrice());
            wishlistDTO.setProductImageUrl(wishlist.getProduct().getProductImage());
            wishlistDTO.setUserId(wishlist.getUser().getUserId());
            wishlistDTO.setProductId(wishlist.getProduct().getProductId());
            return wishlistDTO;
        }).toList();
        return wishlistDTOS;
    }

    public WishlistDTO addProductToWishlist(Long userId, Long productId) {
        Optional<Wishlist> existing = wishlistRepository.findByUserIdAndProductId(userId, productId);
        if(existing.isPresent()) {
            throw new RuntimeException("Product already in wishlist!");
        }

        Optional<Product> existingProduct = productRepository.findById(productId);

        User user = new User();
        user.setUserId(userId);

        Product product = new Product();
        product.setProductId(productId);
        product.setProductImage(existingProduct.get().getProductImage());

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return modelMapper.map(savedWishlist, WishlistDTO.class);
    }

    public void removeProductFromWishlist(Long userId, Long productId) {
        Wishlist wishlist = wishlistRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));
        wishlistRepository.delete(wishlist);
    }
}
