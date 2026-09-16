package com.ait.app.service;

import java.util.List;

import com.ait.app.requestbody.RestaurantDetailsDto;
import com.ait.app.requestbody.RestaurantRequestDto;
import com.ait.app.requestbody.RestaurantResponseDto;

public interface RestaurantService {
	
	RestaurantResponseDto addRestaurant(RestaurantRequestDto dto);
	RestaurantDetailsDto getRestaurantById(int id);
	 List<RestaurantDetailsDto> getRestaurantsByCuisine(String cuisine);

}
