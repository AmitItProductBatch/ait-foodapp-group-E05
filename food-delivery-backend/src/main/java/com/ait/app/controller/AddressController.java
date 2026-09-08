package com.ait.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.Service.AddressService;
import com.ait.app.requestbody.AddressDto;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping
	public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto){
		AddressDto created = addressService.createAddress(addressDto);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<AddressDto>> getAllAddresses() {
		return ResponseEntity.ok(addressService.getAllAddresses());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AddressDto> getAddressById(@PathVariable int id){
		return ResponseEntity.ok(addressService.getAddressById(id));
	}

}
