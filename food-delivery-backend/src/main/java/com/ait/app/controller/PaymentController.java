package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.PaymentOrderRequestDto;
import com.ait.app.requestbody.PaymentOrderResponseDto;
import com.ait.app.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/create-order")
	public ResponseEntity<PaymentOrderResponseDto> createPaymentOrder(
			@RequestBody PaymentOrderRequestDto dto) {
		
		PaymentOrderResponseDto response = paymentService.createPaymentOrder(dto);
		
		return new ResponseEntity<>(
				response,
				HttpStatus.OK);
	}

}
