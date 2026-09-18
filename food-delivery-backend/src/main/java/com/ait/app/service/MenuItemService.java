package com.ait.app.service;

import java.util.List;

import com.ait.app.requestbody.CategoryMenuDto;
import com.ait.app.requestbody.MenuItemRequestDTO;
import com.ait.app.requestbody.MenuItemResponseDTO;

import com.ait.app.model.MenuItem;

public interface MenuItemService {

	MenuItemResponseDTO addMenuItem(MenuItemRequestDTO request);
	MenuItemResponseDTO addMenuItem(int restaurantId, MenuItemRequestDTO request);

	List<CategoryMenuDto> getMenuByRestaurantId(int restaurantId);
	
	void deleteMenuItem(int itemId, Long ownerId);
}
