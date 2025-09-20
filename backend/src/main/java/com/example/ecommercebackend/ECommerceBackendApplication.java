package com.example.ecommercebackend;

import com.example.ecommercebackend.config.AppConstants;
import com.example.ecommercebackend.model.*;
import com.example.ecommercebackend.repository.CategoryRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.repository.RoleRepository;
import com.example.ecommercebackend.repository.UserRepository;
import com.example.ecommercebackend.service.CartService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class ECommerceBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceBackendApplication.class, args);


        /*byte[] keyBytes = new byte[32]; // 256 bits = 32 bytes
        new SecureRandom().nextBytes(keyBytes);
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Base64 Encoded Key: " + base64Key);

        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        SecretKey key = Keys.hmacShaKeyFor(decodedKey);
        System.out.println("Base64 Decoded Key: " + base64Key);*/

    }
}
