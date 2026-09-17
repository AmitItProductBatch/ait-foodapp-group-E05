package com.ait.app.serviceimpl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.MenuItemAlreadyExsistsException;
import com.ait.app.exception.RestaurantCustomException;
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
	public MenuItemResponseDTO addMenuItem(MenuItemRequestDTO request) {
	
		Optional<Restaurant> restaurantOptional =restaurantRepository.findById(request.getRestaurantId());
		 if (!restaurantOptional.isPresent()) {
		        throw new RestaurantCustomException("Restaurant not found with ID", HttpStatus.NOT_FOUND);
		    }
		 
		 Restaurant restaurant = restaurantOptional.get();
		
		 if (menuItemRepository.existsByRestaurantIdAndName(request.getRestaurantId(), request.getName())) {
				throw new MenuItemAlreadyExsistsException("Menu item already exists for this restaurant");
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
	
	

	
	}
	

	
	
	

