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

import com.ait.app.requestbody.RestaurantDetailsDto;
import com.ait.app.requestbody.RestaurantRequestDto;
import com.ait.app.requestbody.RestaurantResponseDto;
import com.ait.app.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	@Autowired
	RestaurantService restaurantService;

	@PostMapping("/add")
	public ResponseEntity<RestaurantResponseDto> createRestaurant(@RequestBody RestaurantRequestDto dto) {

		RestaurantResponseDto rr = restaurantService.addRestaurant(dto);

		return new ResponseEntity<>(rr, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<RestaurantDetailsDto> getRestaurantById(@PathVariable int id) {

	    RestaurantDetailsDto dto = restaurantService.getRestaurantById(id);

	    return new ResponseEntity<>(dto, HttpStatus.OK);
	}
}