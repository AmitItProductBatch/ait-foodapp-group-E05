package com.ait.app.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.app.exception.MenuItemAlreadyExsistsException;
import com.ait.app.model.MenuItem;
import com.ait.app.model.Restaurant;
import com.ait.app.repository.MenuItemRepository;
import com.ait.app.repository.RestaurantRepository;
import com.ait.app.requestbody.MenuItemRequestDTO;
import com.ait.app.requestbody.MenuItemResponseDTO;
import com.ait.app.service.MenuItemService;
@Service
public class MenuItemServiceImpl implements MenuItemService  {
	
	@Autowired
    private RestaurantRepository restaurantRepository;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	@Override
	public MenuItemResponseDTO addMenuItem(int  restaurantId, MenuItemRequestDTO request) {
	
		Restaurant restaurant=restaurantRepository.findById(restaurantId).get();
		
		
		if(menuItemRepository.existsByRestaurantIdAndName(restaurantId, request.getName())) {
			throw new MenuItemAlreadyExsistsException("Menu item already exists for this restaurants");
		}
		
		MenuItem menuItem=new MenuItem();
		menuItem.setName(request.getName());
		menuItem.setDescription(request.getDescription());
		menuItem.setPrice(request.getPrice());
		menuItem.setAvailability(request.getAvailability());
		menuItem.setCategory(request.getCategory());
		
		menuItem.setRestaurant(restaurant);
		
		MenuItem savedMenuItem=menuItemRepository.save(menuItem);
		
		MenuItemResponseDTO response= new MenuItemResponseDTO();
		
		response.setId(savedMenuItem.getId());
		response.setName(savedMenuItem.getName());
		response.setDescription(savedMenuItem.getDescription());
		response.setPrice(savedMenuItem.getPrice());
		response.setAvailability(savedMenuItem.getAvailability());
		response.setCategory(savedMenuItem.getCategory());
		
		return response;
		
		
	}
	@Override
	public MenuItem updateMenuItem(int id, MenuItem menuItem) {

	    MenuItem existingItem = menuItemRepository.findById(id).orElse(null);

	    if (existingItem == null) {
	        return null;
	    }

	    existingItem.setName(menuItem.getName());
	    existingItem.setDescription(menuItem.getDescription());
	    existingItem.setPrice(menuItem.getPrice());
	    existingItem.setAvailability(menuItem.getAvailability());
	    existingItem.setCategory(menuItem.getCategory());

	    return menuItemRepository.save(existingItem);
	}
	

	}
	
	

	
	
	

	
	
	

