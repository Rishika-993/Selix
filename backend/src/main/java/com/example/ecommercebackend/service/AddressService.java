package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.AddressDTO;
import org.springframework.data.domain.Page;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);

    void deleteAddressById(Long addressId);

    AddressDTO fetchAddressById(Long addressId);

    Page<AddressDTO> fetchAddresses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    Page<AddressDTO> fetchCurrentUserAddresses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}
