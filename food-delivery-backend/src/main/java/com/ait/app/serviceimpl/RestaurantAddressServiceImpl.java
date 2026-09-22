package com.ait.app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.RestaurantCustomException;
import com.ait.app.model.Restaurant;
import com.ait.app.model.RestaurantAddress;
import com.ait.app.repository.RestaurantAddressRepository;
import com.ait.app.repository.RestaurantRepository;
import com.ait.app.requestbody.RestaurantAddressRequest;
import com.ait.app.requestbody.RestaurantAddressResponseDto;
import com.ait.app.service.RestaurantAddressService;

@Service
public class RestaurantAddressServiceImpl implements RestaurantAddressService {

	@Autowired
	RestaurantAddressRepository restaurantAddressRepository;

	@Autowired
	RestaurantRepository restaurantRepository;

	@Override
	public RestaurantAddressResponseDto addAddress(RestaurantAddressRequest ResAddReq) {

		if (ResAddReq.getRestaurantId() <= 0) {
			throw new RestaurantCustomException("Restaurant ID is required", HttpStatus.BAD_REQUEST);
		}

		if (ResAddReq.getStreetName() == null || ResAddReq.getStreetName().isEmpty()) {
			throw new RestaurantCustomException("Street name is required", HttpStatus.BAD_REQUEST);
		}
		if (!restaurantRepository.existsById(ResAddReq.getRestaurantId())) {
			throw new RestaurantCustomException("Restaurant not found with id: " + ResAddReq.getRestaurantId(),
					HttpStatus.NOT_FOUND);

		}

		if (ResAddReq.getCity() == null || ResAddReq.getCity().isEmpty()) {
			throw new RestaurantCustomException("City is required", HttpStatus.BAD_REQUEST);

		}

		if (ResAddReq.getPinCode() <= 0) {
			throw new RestaurantCustomException("Pin code is required", HttpStatus.BAD_REQUEST);
		}

		Restaurant restaurant = restaurantRepository.findById(ResAddReq.getRestaurantId()).orElse(null);

		if (restaurant == null) {
			throw new RestaurantCustomException("Restaurant not found", HttpStatus.NOT_FOUND);
		}

		RestaurantAddress address = new RestaurantAddress();

		address.setHouseNo(ResAddReq.getHouseNo());
		address.setStreetName(ResAddReq.getStreetName());
		address.setLandmark(ResAddReq.getLandmark());
		address.setCity(ResAddReq.getCity());
		address.setPinCode(ResAddReq.getPinCode());
		address.setRestaurant(restaurant);

		RestaurantAddress savedAddress = restaurantAddressRepository.save(address);

		return convertToResponse(savedAddress);
	}

	@Override
	public RestaurantAddressResponseDto getAddressById(int id) {

		RestaurantAddress address = restaurantAddressRepository.findById(id).orElse(null);

		if (address == null) {
			throw new RestaurantCustomException("Restaurant address not found", HttpStatus.NOT_FOUND);
		}

		return convertToResponse(address);

	}

	@Override
	public RestaurantAddressResponseDto updateAddress(int id, RestaurantAddressRequest ResAddReq) {

		RestaurantAddress address = restaurantAddressRepository.findById(id).orElse(null);
		if (address == null) {
			throw new RestaurantCustomException("Restaurant address not found", HttpStatus.NOT_FOUND);
		}
		if (ResAddReq.getStreetName() == null || ResAddReq.getStreetName().isEmpty()) {
			throw new RestaurantCustomException("Street name is required", HttpStatus.BAD_REQUEST);
		}
		if (ResAddReq.getCity() == null || ResAddReq.getCity().isEmpty()) {
			throw new RestaurantCustomException("City is required", HttpStatus.BAD_REQUEST);
		}
		if (ResAddReq.getPinCode() <= 0) {
			throw new RestaurantCustomException("Pin code is required", HttpStatus.BAD_REQUEST);
		}
		address.setHouseNo(ResAddReq.getHouseNo());
		address.setStreetName(ResAddReq.getStreetName());
		address.setLandmark(ResAddReq.getLandmark());
		address.setCity(ResAddReq.getCity());
		address.setPinCode(ResAddReq.getPinCode());
		RestaurantAddress updatedAddress = restaurantAddressRepository.save(address);
		return convertToResponse(updatedAddress);
	}

	private RestaurantAddressResponseDto convertToResponse(RestaurantAddress address) {

		RestaurantAddressResponseDto dto = new RestaurantAddressResponseDto();

		dto.setId(address.getId());
		dto.setHouseNo(address.getHouseNo());
		dto.setStreetname(address.getStreetName());
		dto.setLandmark(address.getLandmark());
		dto.setCity(address.getCity());
		dto.setPinCode(address.getPinCode());

		if (address.getRestaurant() != null) {
			dto.setRestaurantId(address.getRestaurant().getId());
		}
		return dto;
	}

}
