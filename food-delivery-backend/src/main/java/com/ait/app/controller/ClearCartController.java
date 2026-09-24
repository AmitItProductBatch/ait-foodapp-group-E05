package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.service.CartService;

@RestController
public class ClearCartController {

    @Autowired
    private CartService cartService;

    @DeleteMapping("/api/cart")
    public ResponseEntity<String> clearCart(@RequestParam Long userId) {

        cartService.clearCart(userId);

        return ResponseEntity.ok("Cart cleared successfully");
    }
}
