package com.ait.app.service;

import com.ait.app.requestbody.MenuItemRequestDTO;
import com.ait.app.requestbody.MenuItemResponseDTO;

import com.ait.app.model.MenuItem;

public interface MenuItemService {

	MenuItemResponseDTO addMenuItem(int restaurantId,MenuItemRequestDTO request);
    MenuItem updateMenuItem(int id, MenuItem menuItem);
	

}
