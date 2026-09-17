package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.CartRequestDto;
import com.ait.app.requestbody.CartResponseDto;
import com.ait.app.service.CartService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/carts")
public class CartController {
	
	@Autowired
	CartService cs;
	
	@PostMapping("/add")
	public ResponseEntity createCart(@RequestBody CartRequestDto dto) {
		CartResponseDto r = cs.addCart(dto);
		return new ResponseEntity<>(r, HttpStatus.CREATED);
	}
	
	
}