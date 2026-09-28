package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.OrderDetailsDto;
import com.ait.app.requestbody.OrderRequestDto;
import com.ait.app.requestbody.OrderResponseDto;
import com.ait.app.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	OrderService os;
	
    @PostMapping
    public ResponseEntity placeOrder(@RequestBody OrderRequestDto dto) {
        OrderResponseDto orderResponse = os.placeOrder(dto);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED); 
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsDto> getOrderDetails(
            @PathVariable Long orderId) {

        OrderDetailsDto orderDetails = os.getOrderDetails(orderId);

        return new ResponseEntity<>(
                orderDetails,
                HttpStatus.OK);
    }

}
