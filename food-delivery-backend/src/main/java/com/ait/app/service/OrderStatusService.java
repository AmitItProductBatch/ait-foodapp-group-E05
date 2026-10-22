package com.ait.app.service;

import com.ait.app.model.Orders;
import com.ait.app.requestbody.OrderStatusUpdateDto;

public interface OrderStatusService {

    Orders updateOrderStatus(Long orderId, OrderStatusUpdateDto dto);

}