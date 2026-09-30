package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.DeliveryFeeRuleRequestDto;
import com.ait.app.requestbody.DeliveryFeeRuleResponseDto;
import com.ait.app.service.DeliveryFeeRuleService;

@RestController
@RequestMapping("/api/prices")
public class DeliveryFeeRuleController {
	
	@Autowired
	private DeliveryFeeRuleService deliveryFeeRuleService;
	
	@PutMapping("/delivery-rules")
	public ResponseEntity<DeliveryFeeRuleResponseDto> updatedDeliveryFeeRule(
			@RequestHeader("X-Admin-Key") String adminKey,
			@RequestBody DeliveryFeeRuleRequestDto dto) {
		
		if(!"admin123".equals(adminKey)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		DeliveryFeeRuleResponseDto response = deliveryFeeRuleService.updateDeliveryFeeRule(dto);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	

}
