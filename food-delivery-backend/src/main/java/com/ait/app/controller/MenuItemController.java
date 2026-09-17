package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.model.Restaurant;
import com.ait.app.repository.RestaurantRepository;
import com.ait.app.requestbody.MenuItemRequestDTO;
import com.ait.app.requestbody.MenuItemResponseDTO;
import com.ait.app.service.MenuItemService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/MenuItems")
public class MenuItemController {
	
	@Autowired
	private MenuItemService menuItemService;
	
	@PostMapping("/menus")
	public ResponseEntity addMenuItem(@RequestBody MenuItemRequestDTO request ){
		MenuItemResponseDTO saved = menuItemService.addMenuItem(request);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	
	
	
}
	


