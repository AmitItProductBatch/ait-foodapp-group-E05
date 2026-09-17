package com.ait.app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ait.app.requestbody.CategoryMenuDto;
import com.ait.app.requestbody.MenuItemRequestDTO;
import com.ait.app.requestbody.MenuItemResponseDTO;
import com.ait.app.service.MenuItemService;





@RestController
@RequestMapping("/MenuItems")
public class MenuItemController {

	@Autowired
	MenuItemService menuItemService;
	
	@PostMapping("/menus")
	public ResponseEntity addMenuItem(@RequestBody MenuItemRequestDTO request ){
		MenuItemResponseDTO saved = menuItemService.addMenuItem(request);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
		
	@DeleteMapping("/{itemId}")
	public ResponseEntity<Void> deleteMenuItem(
	      @PathVariable int itemId,
	      @RequestParam Long ownerId) {

		menuItemService.deleteMenuItem(itemId, ownerId);

	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    
	}

	@GetMapping("/{restaurantId}/menu")
	public ResponseEntity<List<CategoryMenuDto>> getMenu(@PathVariable int restaurantId) {

		List<CategoryMenuDto> menu = menuItemService.getMenuByRestaurantId(restaurantId);

		return new ResponseEntity<List<CategoryMenuDto>>(menu, HttpStatus.OK);
	}
	


}
