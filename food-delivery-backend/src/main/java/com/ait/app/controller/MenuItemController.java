package com.ait.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.model.Restaurant;
import com.ait.app.repository.RestaurantRepository;
import com.ait.app.requestbody.CategoryMenuDto;
import com.ait.app.requestbody.MenuItemRequestDTO;
import com.ait.app.requestbody.MenuItemResponseDTO;
import com.ait.app.service.MenuItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/MenuItems")
public class MenuItemController {

	@Autowired
	private MenuItemService menuItemService;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@PostMapping("/menus")
	public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
		Restaurant saved = restaurantRepository.save(restaurant);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@PostMapping("/{restaurantId}/menu")
	public ResponseEntity<MenuItemResponseDTO> addMenuItem(@PathVariable int restaurantId,
			@Valid @RequestBody MenuItemRequestDTO request) {

		MenuItemResponseDTO response = menuItemService.addMenuItem(restaurantId, request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@GetMapping("/{restaurantId}/menu")
	public ResponseEntity<List<CategoryMenuDto>> getMenu(@PathVariable int restaurantId) {

		List<CategoryMenuDto> menu = menuItemService.getMenuByRestaurantId(restaurantId);

		return new ResponseEntity<List<CategoryMenuDto>>(menu, HttpStatus.OK);
	}

}
