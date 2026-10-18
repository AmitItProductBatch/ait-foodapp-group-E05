package com.ait.app.service;

import java.util.List;


import com.ait.app.requestbody.CategoryMenuDto;
import com.ait.app.requestbody.MenuItemRequestDTO;
import com.ait.app.requestbody.MenuItemResponseDTO;


public interface MenuItemService {

	MenuItemResponseDTO addMenuItem(MenuItemRequestDTO request);

	List<CategoryMenuDto> getMenuByRestaurantId(int restaurantId);
	
	void deleteMenuItem(int itemId, Long ownerId);

	MenuItemResponseDTO updateMenuItem(int id, MenuItemRequestDTO request);

	
}
