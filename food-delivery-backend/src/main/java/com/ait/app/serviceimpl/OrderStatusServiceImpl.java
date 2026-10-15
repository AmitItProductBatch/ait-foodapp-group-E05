package com.ait.app.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ait.app.model.OrderStatus;
import com.ait.app.model.OrderStatusHistory;
import com.ait.app.model.Orders;
import com.ait.app.repository.OrderRepository;
import com.ait.app.repository.OrderStatusHistoryRepository;
import com.ait.app.requestbody.OrderStatusUpdateDto;
import com.ait.app.service.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired
    private OrderRepository or;

    @Autowired
    private OrderStatusHistoryRepository historyRepository;

    @Override
    @Transactional
    public Orders updateOrderStatus(Long orderId, OrderStatusUpdateDto dto) {

        Orders order = or.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order not found"));

        OrderStatus currentStatus = OrderStatus.valueOf(order.getStatus());
        OrderStatus newStatus = dto.getStatus();

        boolean validTransition =
                (currentStatus == OrderStatus.PLACED
                        && newStatus == OrderStatus.CONFIRMED)
                || (currentStatus == OrderStatus.CONFIRMED
                        && newStatus == OrderStatus.PREPARING)
                || (currentStatus == OrderStatus.PREPARING
                        && newStatus == OrderStatus.OUT_FOR_DELIVERY)
                || (currentStatus == OrderStatus.OUT_FOR_DELIVERY
                        && newStatus == OrderStatus.DELIVERED);

        if (!validTransition) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid order status transition");
        }

        LocalDateTime now = LocalDateTime.now();

        order.setStatus(newStatus.name());
        order.setUpdatedAt(now);

        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrderId(order.getId());
        history.setStatus(newStatus.name());
        history.setChangedAt(now);

        historyRepository.save(history);

        return or.save(order);
    }
}
