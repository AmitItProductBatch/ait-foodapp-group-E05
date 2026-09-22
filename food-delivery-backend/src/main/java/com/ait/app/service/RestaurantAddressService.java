package com.ait.app.service;

import com.ait.app.requestbody.RestaurantAddressRequest;
import com.ait.app.requestbody.RestaurantAddressResponseDto;

public interface RestaurantAddressService {
	
	RestaurantAddressResponseDto addAddress(RestaurantAddressRequest ResAddReq);
	
	RestaurantAddressResponseDto getAddressById(int id);
	
	RestaurantAddressResponseDto updateAddress(int id, RestaurantAddressRequest ResAddReq);

}
