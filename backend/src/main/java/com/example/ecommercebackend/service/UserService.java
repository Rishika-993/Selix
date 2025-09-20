package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.UserDTO;

public interface UserService {
    UserDTO fetchCurrentlyLoggedInUserDetails();

    UserDTO updateUserDetails(UserDTO userDTO, Long userId);
}
