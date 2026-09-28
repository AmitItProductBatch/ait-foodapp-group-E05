package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.OrderTotalRequestDto;
import com.ait.app.requestbody.OrderTotalResponseDto;
import com.ait.app.service.OrderTotalService;

@RestController
@RequestMapping("/api/orders")
public class OrderTotalController {

    @Autowired
    private OrderTotalService orderTotalService;

    @PostMapping("/total")
    public OrderTotalResponseDto calculateOrderTotal(
            @RequestBody OrderTotalRequestDto request) {

        return orderTotalService.calculateOrderTotal(request);
    }
}