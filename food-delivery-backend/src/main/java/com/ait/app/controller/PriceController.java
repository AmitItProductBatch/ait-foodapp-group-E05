package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.PriceCalculationRequestDto;
import com.ait.app.requestbody.PriceCalculationResponseDto;
import com.ait.app.service.PriceService;

@RestController
@RequestMapping("api/prices")
public class PriceController {

	@Autowired
	private PriceService priceService;

	@PostMapping("/calculate")
	public ResponseEntity<PriceCalculationResponseDto> calculatePrice(@RequestBody PriceCalculationRequestDto dto) {

		PriceCalculationResponseDto response = priceService.calculatePrice(dto);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
