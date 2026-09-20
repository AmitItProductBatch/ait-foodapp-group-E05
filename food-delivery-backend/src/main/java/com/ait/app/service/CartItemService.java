package com.ait.app.service;

import com.ait.app.requestbody.CartItemRequestDto;
import com.ait.app.requestbody.CartItemResponseDto;
import com.ait.app.requestbody.CartResponseDto;
import com.ait.app.requestbody.QuantityUpdateDTO;

public interface CartItemService {

	CartItemResponseDto addToCart(CartItemRequestDto dto);

	CartResponseDto updateQuantity(Long itemId, QuantityUpdateDTO dto);
}