package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.DeliveryFeeRequestDto;
import com.ait.app.requestbody.DeliveryFeeResponseDto;
import com.ait.app.service.DeliveryFeeService;

@RestController
@RequestMapping("/api/prices/delivery-fee")
public class DeliveryFeeController {
	
    @Autowired
    DeliveryFeeService ds;
    
    @PostMapping
    public ResponseEntity calculateDeliveryFee(@RequestBody DeliveryFeeRequestDto dto) {
        DeliveryFeeResponseDto fee = ds.calculateFee(dto);
        return new ResponseEntity<>(fee, HttpStatus.OK); 
    }

}
