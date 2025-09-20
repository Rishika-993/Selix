package com.example.ecommercebackend.service.impl;

import com.example.ecommercebackend.dto.AddressDTO;
import com.example.ecommercebackend.exception.custom.ResourceNotFoundException;
import com.example.ecommercebackend.model.Address;
import com.example.ecommercebackend.model.User;
import com.example.ecommercebackend.repository.AddressRepository;
import com.example.ecommercebackend.repository.UserRepository;
import com.example.ecommercebackend.service.AddressService;
import com.example.ecommercebackend.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AuthUtil authUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, AuthUtil authUtil, ModelMapper modelMapper, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.authUtil = authUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        User loggedInUser = authUtil.loggedInUser();
        Address address = modelMapper.map(addressDTO, Address.class);
        List<Address> addresses = loggedInUser.getAddresses();
        addresses.add(address);
        loggedInUser.setAddresses(addresses);
        address.setUser(loggedInUser);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        return addressRepository.findById(addressId)
                .map(existingAddress -> {
                    existingAddress.setStreet(addressDTO.getStreet());
                    Address updatedAddress = addressRepository.save(existingAddress);
                    return modelMapper.map(updatedAddress, AddressDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));
    }

    @Override
    public void deleteAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));
        User user = address.getUser();
        user.getAddresses().removeIf(address1 -> address1.getAddressId().equals(addressId));
        userRepository.save(user);
//        addressRepository.deleteById(addressId);
        addressRepository.delete(address);
    }

    @Override
    public AddressDTO fetchAddressById(Long addressId) {
        Optional<Address> address = addressRepository.findById(addressId);
        return modelMapper.map(address.get(), AddressDTO.class);
    }

    @Override
    public Page<AddressDTO> fetchAddresses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Address> addresses = addressRepository.findAll(pageable);
        Page<AddressDTO> addressDTOS = addresses.map(address -> modelMapper.map(address, AddressDTO.class));
        return addressDTOS;
    }

    @Override
    public Page<AddressDTO> fetchCurrentUserAddresses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        User user = authUtil.loggedInUser();

        // Convert addresses to DTO
        List<AddressDTO> dtoList = user.getAddresses()
                .stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .sorted((a1, a2) -> {
                    // Basic sorting logic (can be extended for dynamic fields)
                    if ("asc".equalsIgnoreCase(sortOrder)) {
                        return a1.getAddressId().compareTo(a2.getAddressId());
                    } else {
                        return a2.getAddressId().compareTo(a1.getAddressId());
                    }
                })
                .collect(Collectors.toList());

        // Manual pagination
        int start = pageNumber * pageSize;
        int end = Math.min(start + pageSize, dtoList.size());

        if (start > dtoList.size()) {
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(pageNumber, pageSize), dtoList.size());
        }

        List<AddressDTO> pageContent = dtoList.subList(start, end);
        return new PageImpl<>(pageContent, PageRequest.of(pageNumber, pageSize), dtoList.size());
    }

}
