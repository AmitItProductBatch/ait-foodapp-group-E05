package com.ait.app.service;

import com.ait.app.requestbody.MenuItemRequestDTO;
import com.ait.app.requestbody.MenuItemResponseDTO;

public interface MenuItemService {

	MenuItemResponseDTO addMenuItem(int  restaurantId,MenuItemRequestDTO request);
}
