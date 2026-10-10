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
import com.ait.app.exception.MenuItemNotFoundException;
import com.ait.app.exception.MenuItemOwnershipException;
import com.ait.app.model.Category;
import com.ait.app.model.MenuItem;
import com.ait.app.model.Restaurant;
import com.ait.app.repository.CategoryRepo;
import com.ait.app.repository.MenuItemRepository;
import com.ait.app.repository.RestaurantRepository;
import com.ait.app.requestbody.CategoryMenuDto;
import com.ait.app.requestbody.MenuItemRequestDTO;
import com.ait.app.requestbody.MenuItemResponseDTO;
import com.ait.app.service.MenuItemService;

@Service
public class MenuItemServiceImpl implements MenuItemService {


@Autowired
RestaurantRepository restaurantRepository;

@Autowired
MenuItemRepository menuItemRepository;

@Autowired
private CategoryRepo categoryRepo;

@Override
public MenuItemResponseDTO addMenuItem(MenuItemRequestDTO request) {

    Optional<Restaurant> restaurantOptional =
            restaurantRepository.findById(request.getRestaurantId());

    if (!restaurantOptional.isPresent()) {
        throw new RestaurantCustomException(
                "Restaurant not found with id: " + request.getRestaurantId(),
                HttpStatus.NOT_FOUND);
    }

    Restaurant restaurant = restaurantOptional.get();

    if (menuItemRepository.existsByRestaurantIdAndName(
            request.getRestaurantId(), request.getName())) {
        throw new MenuItemAlreadyExsistsException(
                "Menu item already exists for this restaurant");
    }

    Optional<Category> categoryOptional =
            categoryRepo.findById(request.getCategoryId());

    if (!categoryOptional.isPresent()) {
        throw new IllegalArgumentException(
                "Category not found with id: " + request.getCategoryId());
    }

    Category category = categoryOptional.get();

    if (!category.isActive()) {
        throw new IllegalArgumentException("Category is inactive");
    }

    MenuItem menuItem = new MenuItem();
    menuItem.setName(request.getName());
    menuItem.setDescription(request.getDescription());
    menuItem.setPrice(request.getPrice());
    menuItem.setAvailability(request.getAvailability());
    menuItem.setCategory(category);
    menuItem.setRestaurant(restaurant);

    MenuItem savedMenuItem = menuItemRepository.save(menuItem);

    MenuItemResponseDTO response = new MenuItemResponseDTO();
    response.setId(savedMenuItem.getId());
    response.setName(savedMenuItem.getName());
    response.setDescription(savedMenuItem.getDescription());
    response.setPrice(savedMenuItem.getPrice());
    response.setAvailability(savedMenuItem.getAvailability());
    response.setCategoryId(savedMenuItem.getCategory().getId());
    response.setCategory(savedMenuItem.getCategory().getCategoryName());

    return response;
}

@Override
public void deleteMenuItem(int itemId, Long ownerId) {

    Optional<MenuItem> optionalMenuItem =
            menuItemRepository.findById(itemId);

    if (optionalMenuItem.isEmpty()) {
        throw new MenuItemNotFoundException("Menu item not found");
    }

    MenuItem menuItem = optionalMenuItem.get();
    Restaurant restaurant = menuItem.getRestaurant();

    if (restaurant.getOwner() == null) {
        throw new MenuItemOwnershipException("Restaurant has no owner");
    }

    if (!restaurant.getOwner().getId().equals(ownerId)) {
        throw new MenuItemOwnershipException(
                "You are not the owner of this restaurant");
    }

    menuItemRepository.delete(menuItem);
}

@Override
public List<CategoryMenuDto> getMenuByRestaurantId(int restaurantId) {

	 Optional<Restaurant> restaurantOptional =
	            restaurantRepository.findByIdAndActiveTrue(restaurantId);

	    if (restaurantOptional.isEmpty()) {
	        throw new RestaurantCustomException(
	                "Restaurant not found or inactive",
	                HttpStatus.NOT_FOUND);
	    }

	    List<MenuItem> menuItems =
	            menuItemRepository.findByRestaurantIdAndAvailabilityTrue(restaurantId);

	    Map<String, List<MenuItemResponseDTO>> groupedItems =
	            new LinkedHashMap<String, List<MenuItemResponseDTO>>();

	    for (int i = 0; i < menuItems.size(); i++) {

	        MenuItem menuItem = menuItems.get(i);

	        MenuItemResponseDTO itemDto = new MenuItemResponseDTO();

	        itemDto.setId(menuItem.getId());
	        itemDto.setName(menuItem.getName());
	        itemDto.setDescription(menuItem.getDescription());
	        itemDto.setPrice(menuItem.getPrice());
	        itemDto.setImage(menuItem.getImage());
	        itemDto.setAvailability(menuItem.getAvailability());

	        Category menuCategory = menuItem.getCategory();

	        if (menuCategory == null) {
	            continue;
	        }

	        itemDto.setCategoryId(menuCategory.getId());
	        itemDto.setCategory(menuCategory.getCategoryName());

	        String category = menuCategory.getCategoryName();

	        List<MenuItemResponseDTO> itemsInCategory =
	                groupedItems.get(category);

	        if (itemsInCategory == null) {
	            itemsInCategory = new ArrayList<MenuItemResponseDTO>();
	            groupedItems.put(category, itemsInCategory);
	        }

	        itemsInCategory.add(itemDto);
	    }

	    List<CategoryMenuDto> response =
	            new ArrayList<CategoryMenuDto>();

	    for (Map.Entry<String, List<MenuItemResponseDTO>> entry
	            : groupedItems.entrySet()) {

	        CategoryMenuDto categoryMenuDto = new CategoryMenuDto();

	        categoryMenuDto.setCategory(entry.getKey());
	        categoryMenuDto.setItems(entry.getValue());

	        response.add(categoryMenuDto);
	    }

	    return response;
	}
@Override
public MenuItemResponseDTO updateMenuItem(int id, MenuItemRequestDTO request) {

    Optional<MenuItem> menuItemOptional = menuItemRepository.findById(id);

    if (!menuItemOptional.isPresent()) {
        throw new MenuItemNotFoundException("Menu item not found with id: " + id);
    }

    Optional<Category> categoryOptional =
            categoryRepo.findById(request.getCategoryId());

    if (!categoryOptional.isPresent()) {
        throw new IllegalArgumentException(
                "Category not found with id: " + request.getCategoryId());
    }

    Category category = categoryOptional.get();

    if (!category.isActive()) {
        throw new IllegalArgumentException("Category is inactive");
    }

    MenuItem menuItem = menuItemOptional.get();
    menuItem.setName(request.getName());
    menuItem.setDescription(request.getDescription());
    menuItem.setPrice(request.getPrice());
    menuItem.setAvailability(request.getAvailability());
    menuItem.setCategory(category);

    MenuItem updatedMenuItem = menuItemRepository.save(menuItem);

    MenuItemResponseDTO response = new MenuItemResponseDTO();
    response.setId(updatedMenuItem.getId());
    response.setName(updatedMenuItem.getName());
    response.setDescription(updatedMenuItem.getDescription());
    response.setPrice(updatedMenuItem.getPrice());
    response.setAvailability(updatedMenuItem.getAvailability());
    response.setCategoryId(updatedMenuItem.getCategory().getId());
    response.setCategory(updatedMenuItem.getCategory().getCategoryName());

    return response;
}


}
