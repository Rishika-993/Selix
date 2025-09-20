package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.dto.UserDTO;
import com.example.ecommercebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> fetchCurrentlyLoggedInUserDetails() {
        UserDTO userDTO = userService.fetchCurrentlyLoggedInUserDetails();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody UserDTO userDTO, @PathVariable Long userId) {
        UserDTO updatedUserDTO = userService.updateUserDetails(userDTO, userId);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }
}
