package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ait.app.requestbody.CartItemRequestDto;
import com.ait.app.requestbody.CartItemResponseDto;
import com.ait.app.requestbody.CartResponseDto;
import com.ait.app.requestbody.QuantityUpdateDTO;
import com.ait.app.service.CartItemService;

@RestController
@RequestMapping("/cartitems")
public class CartItemController {

	@Autowired
	CartItemService cis;
	
	@Autowired
	CartItemService cartItemService;

	@PostMapping
	public ResponseEntity<CartItemResponseDto> addToCart(@RequestBody CartItemRequestDto dto) {
		CartItemResponseDto itemResponse = cis.addToCart(dto);
		return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/items/{itemId}")
	public ResponseEntity<String> deleteCartItem(@PathVariable int itemId) {

		cartItemService.deleteCartItem(itemId);
     return ResponseEntity.ok("Cart item deleted successfully");
	}
  @PutMapping("/{itemId}")
    public ResponseEntity<CartResponseDto>updateQuantity(@PathVariable Long itemId,@RequestBody QuantityUpdateDTO dto){
    	CartResponseDto response=cis.updateQuantity(itemId, dto);
    	return new ResponseEntity<>(response,HttpStatus.OK);
    	
    	
    }
}
