package com.ait.app.service;

import com.ait.app.requestbody.OrderRequestDto;
import com.ait.app.requestbody.OrderResponseDto;

public interface OrderService {
	
    OrderResponseDto placeOrder(OrderRequestDto dto);

}
