package com.ait.app.service;

import com.ait.app.requestbody.CartRequestDto;
import com.ait.app.requestbody.CartResponseDto;

public interface CartService {
	
	CartResponseDto addCart(CartRequestDto dto);

}
