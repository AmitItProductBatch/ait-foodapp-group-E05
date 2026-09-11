package com.ait.app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.RestaurantCustomException;
import com.ait.app.model.Restaurant;
import com.ait.app.model.User;
import com.ait.app.repository.RestaurantRepository;
import com.ait.app.repository.UserRepository;
import com.ait.app.requestbody.RestaurantRequestDto;
import com.ait.app.requestbody.RestaurantResponseDto;
import com.ait.app.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	UserRepository ur;

	@Autowired
	RestaurantRepository rr;

	@Override
	public RestaurantResponseDto addRestaurant(RestaurantRequestDto dto) {

		if (dto.getName() == null || dto.getName().isEmpty()) {
			throw new RestaurantCustomException("Restaurant name is required", HttpStatus.BAD_REQUEST);
		}

		if (dto.getAddress() == null || dto.getAddress().isEmpty()) {
			throw new RestaurantCustomException("Restaurant address is required", HttpStatus.BAD_REQUEST);
		}

		if (dto.getCuisine() == null || dto.getCuisine().isEmpty()) {
			throw new RestaurantCustomException("Restaurant cuisine is required", HttpStatus.BAD_REQUEST);
		}

		if (dto.getContact() == null || dto.getContact().isEmpty()) {
			throw new RestaurantCustomException("Restaurant contact is required", HttpStatus.BAD_REQUEST);
		}

		if (dto.getOwnerId() == null) {
			throw new RestaurantCustomException("Owner ID is required", HttpStatus.BAD_REQUEST);
		}

		User owner = ur.findById(dto.getOwnerId()).orElse(null);

		if (owner == null) {
			throw new RestaurantCustomException("Owner not found.", HttpStatus.NOT_FOUND);
		}

		Restaurant restaurant = new Restaurant();

		restaurant.setName(dto.getName());
		restaurant.setAddress(dto.getAddress());
		restaurant.setCuisine(dto.getCuisine());
		restaurant.setContact(dto.getContact());
		restaurant.setOwner(owner);

		Restaurant savedRestaurant = rr.save(restaurant);

		RestaurantResponseDto responseDto = new RestaurantResponseDto();
		responseDto.setRestaurantId(savedRestaurant.getId());
		responseDto.setOwnerId(owner.getId());

		return responseDto;

	}

}
