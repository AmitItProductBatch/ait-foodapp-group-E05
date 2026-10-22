package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.model.Orders;
import com.ait.app.requestbody.OrderStatusUpdateDto;
import com.ait.app.service.OrderStatusService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderStatusController {

	@Autowired
	OrderStatusService os;

	@PutMapping("/{orderId}/status")
	public ResponseEntity<Orders> updateOrderStatus(@PathVariable Long orderId,
			@Valid @RequestBody OrderStatusUpdateDto dto) {

		Orders updatedOrder = os.updateOrderStatus(orderId, dto);

		return ResponseEntity.ok(updatedOrder);
	}
}