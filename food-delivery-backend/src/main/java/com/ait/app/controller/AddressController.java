package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ait.app.service.AddressService;
import com.ait.app.requestbody.AddressRequestDto;

@RestController
@RequestMapping("/users")
public class AddressController {

    @Autowired
    private AddressService as;

    @PostMapping("/{userId}/addresses")
    public ResponseEntity<Long> createAddress(@PathVariable Long userId,@RequestBody AddressRequestDto dto) {
        
        Long addressId = as.CreateAddress(userId, dto);
        return new ResponseEntity<>(addressId, HttpStatus.CREATED);
    }
}