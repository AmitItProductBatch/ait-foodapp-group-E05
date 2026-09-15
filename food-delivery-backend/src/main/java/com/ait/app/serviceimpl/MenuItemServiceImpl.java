package com.ait.app.serviceimpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import com.ait.app.requestbody.CategoryMenuDto;
import com.ait.app.requestbody.MenuItemRequestDTO;
import com.ait.app.requestbody.MenuItemResponseDTO;
import com.ait.app.service.MenuItemService;

@Service
public class MenuItemServiceImpl implements MenuItemService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Override
	public MenuItemResponseDTO addMenuItem(int restaurantId, MenuItemRequestDTO request) {

		Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

		if (menuItemRepository.existsByRestaurantIdAndName(restaurantId, request.getName())) {
			throw new MenuItemAlreadyExsistsException("Menu item already exists for this restaurants");
		}

		MenuItem menuItem = new MenuItem();
		menuItem.setName(request.getName());
		menuItem.setDescription(request.getDescription());
		menuItem.setPrice(request.getPrice());
		menuItem.setAvailability(request.getAvailability());
		menuItem.setCategory(request.getCategory());

		menuItem.setRestaurant(restaurant);

		MenuItem savedMenuItem = menuItemRepository.save(menuItem);

		MenuItemResponseDTO response = new MenuItemResponseDTO();

		response.setId(savedMenuItem.getId());
		response.setName(savedMenuItem.getName());
		response.setDescription(savedMenuItem.getDescription());
		response.setPrice(savedMenuItem.getPrice());
		response.setAvailability(savedMenuItem.getAvailability());
		response.setCategory(savedMenuItem.getCategory());

		return response;

	}

	@Override
	public List<CategoryMenuDto> getMenuByRestaurantId(int restaurantId) {

		Optional<Restaurant> restaurantOptional = restaurantRepository.findByIdAndActiveTrue(restaurantId);

		if (restaurantOptional.isEmpty()) {
			throw new RestaurantCustomException("Restaurant not found or inactive", HttpStatus.NOT_FOUND);
		}

		List<MenuItem> menuItems = menuItemRepository.findByRestaurantIdAndAvailabilityTrue(restaurantId);

		Map<String, List<MenuItemResponseDTO>> groupedItems = new LinkedHashMap<String, List<MenuItemResponseDTO>>();

		for (int i = 0; i < menuItems.size(); i++) {

			MenuItem menuItem = menuItems.get(i);

			MenuItemResponseDTO itemDto = new MenuItemResponseDTO();
			itemDto.setId(menuItem.getId());
			itemDto.setName(menuItem.getName());
			itemDto.setDescription(menuItem.getDescription());
			itemDto.setPrice(menuItem.getPrice());
			itemDto.setImage(menuItem.getImage());

			String category = menuItem.getCategory();

			List<MenuItemResponseDTO> itemsInCategory = groupedItems.get(category);

			if (itemsInCategory == null) {
				itemsInCategory = new ArrayList<MenuItemResponseDTO>();
				groupedItems.put(category, itemsInCategory);
			}

			itemsInCategory.add(itemDto);
		}

		List<CategoryMenuDto> response = new ArrayList<CategoryMenuDto>();

		for (Map.Entry<String, List<MenuItemResponseDTO>> entry : groupedItems.entrySet()) {
			CategoryMenuDto categoryMenuDto = new CategoryMenuDto();
			categoryMenuDto.setCategory(entry.getKey());
			categoryMenuDto.setItems(entry.getValue());
			response.add(categoryMenuDto);
		}

		return response;
	}

}
