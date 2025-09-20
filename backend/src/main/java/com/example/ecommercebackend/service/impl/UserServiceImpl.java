package com.example.ecommercebackend.service.impl;

import com.example.ecommercebackend.dto.UserDTO;
import com.example.ecommercebackend.exception.custom.ResourceNotFoundException;
import com.example.ecommercebackend.model.User;
import com.example.ecommercebackend.repository.UserRepository;
import com.example.ecommercebackend.service.UserService;
import com.example.ecommercebackend.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDTO fetchCurrentlyLoggedInUserDetails() {
        User loggedInUser = authUtil.loggedInUser();
        Optional<User> optionalUser = userRepository.findById(loggedInUser.getUserId());
        return modelMapper.map(optionalUser.get(), UserDTO.class);
    }

    @Override
    public UserDTO updateUserDetails(UserDTO userDTO, Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(String.format("User not found with ID: ", userId)));
        if (userDTO.getUserName() != null)
            existingUser.setUserName(userDTO.getUserName());

        if (userDTO.getEmail() != null)
            existingUser.setEmail(userDTO.getEmail());

        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }
}
