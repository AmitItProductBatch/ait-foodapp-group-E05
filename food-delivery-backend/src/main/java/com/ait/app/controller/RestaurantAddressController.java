package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.RestaurantAddressRequest;
import com.ait.app.requestbody.RestaurantAddressResponseDto;
import com.ait.app.service.RestaurantAddressService;

@RestController
@RequestMapping("/api/restaurant-addresses")
public class RestaurantAddressController {

	@Autowired
	RestaurantAddressService restaurantAddressService;

	@PostMapping
	public ResponseEntity<RestaurantAddressResponseDto> addAddress(
			@RequestBody RestaurantAddressRequest restaurantAddressRequest) {

		RestaurantAddressResponseDto response = restaurantAddressService.addAddress(restaurantAddressRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestaurantAddressResponseDto> getAddressById(@PathVariable int id) {

		RestaurantAddressResponseDto response = restaurantAddressService.getAddressById(id);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	public ResponseEntity<RestaurantAddressResponseDto> updateAddress(@PathVariable int id,
			@RequestBody RestaurantAddressRequest restaurantAddressRequest) {

		RestaurantAddressResponseDto response = restaurantAddressService.updateAddress(id, restaurantAddressRequest);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
