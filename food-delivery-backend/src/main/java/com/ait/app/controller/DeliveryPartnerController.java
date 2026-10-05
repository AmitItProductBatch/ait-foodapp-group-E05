package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.DeliveryPartnerRequestDto;
import com.ait.app.requestbody.DeliveryPartnerResponseDto;
import com.ait.app.service.DeliveryPartnerService;

@RestController
@RequestMapping("/api/delivery-partners")
public class DeliveryPartnerController {
	
	@Autowired
	DeliveryPartnerService dps;
	
	@PostMapping
	public ResponseEntity registerDeliveryPartner(@RequestBody DeliveryPartnerRequestDto dto) {
		DeliveryPartnerResponseDto dpr = dps.createDeliveryPartner(dto);
		return new ResponseEntity(dpr, HttpStatus.CREATED);
	}

}
