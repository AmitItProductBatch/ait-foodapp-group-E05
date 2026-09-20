package com.ait.app.service;

import com.ait.app.requestbody.CartItemRequestDto;
import com.ait.app.requestbody.CartItemResponseDto;

public interface CartItemService {
	
	CartItemResponseDto addToCart(CartItemRequestDto dto);
	
	void deleteCartItem(int itemId);

}