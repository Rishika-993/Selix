package com.example.ecommercebackend.dto;

import com.example.ecommercebackend.model.Address;
import com.example.ecommercebackend.model.Cart;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.model.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Long userId;
    private String userName;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private Set<Product> products;
    private List<Address> addresses = new ArrayList<>();
}
