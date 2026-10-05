package com.ait.app.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.ait.app.requestbody.OrderDetailsDto;
import com.ait.app.requestbody.OrderHistoryDto;
import com.ait.app.requestbody.OrderRequestDto;
import com.ait.app.requestbody.OrderResponseDto;

public interface OrderService {

    OrderResponseDto placeOrder(OrderRequestDto dto);

    OrderDetailsDto getOrderDetails(Long orderId);

    Page<OrderHistoryDto> getOrderHistory(
            Long userId,
            int page,
            int size,
            String status,
            LocalDateTime fromDate,
            LocalDateTime toDate);
}