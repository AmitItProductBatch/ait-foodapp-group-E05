package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ait.app.requestbody.CartItemRequestDto;
import com.ait.app.requestbody.CartItemResponseDto;
import com.ait.app.service.CartItemService;

@RestController
@RequestMapping("/cartitems")
public class CartItemController {

    @Autowired
    CartItemService cis;

    @PostMapping
    public ResponseEntity<CartItemResponseDto> addToCart(@RequestBody CartItemRequestDto dto) {
        CartItemResponseDto itemResponse = cis.addToCart(dto);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }
}
