package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.dto.AddressDTO;
import com.example.ecommercebackend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.ecommercebackend.config.AppConstants.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDTO> saveAddress(@RequestBody AddressDTO addressDTO) {
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
        AddressDTO updatedAddressDTO = addressService.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(updatedAddressDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Long addressId) {
        addressService.deleteAddressById(addressId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> fetchAddressById(@PathVariable Long addressId) {
        return new ResponseEntity<>(addressService.fetchAddressById(addressId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<AddressDTO>> getAddressesWthPagination(@RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = ADDRESS_SORT_BY, required = false) String sortBy, @RequestParam(value = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder) {
        return new ResponseEntity<>(addressService.fetchAddresses(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @GetMapping("/users/current")
    public ResponseEntity<Page<AddressDTO>> fetchCurrentUserAddresses(@RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = ADDRESS_SORT_BY, required = false) String sortBy, @RequestParam(value = "sortOrder", defaultValue = SORT_DIR, required = false) String sortOrder) {
        return new ResponseEntity<>(addressService.fetchCurrentUserAddresses(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

}
