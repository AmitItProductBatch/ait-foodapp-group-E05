package com.ait.app.service;

import com.ait.app.requestbody.OrderTotalRequestDto;
import com.ait.app.requestbody.OrderTotalResponseDto;

public interface OrderTotalService {

    OrderTotalResponseDto calculateOrderTotal(OrderTotalRequestDto request);

}
