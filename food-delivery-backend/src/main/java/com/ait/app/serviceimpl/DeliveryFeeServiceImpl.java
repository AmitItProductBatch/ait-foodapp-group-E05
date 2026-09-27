package com.ait.app.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.PriceCustomException;
import com.ait.app.model.Address;
import com.ait.app.model.RestaurantAddress;
import com.ait.app.repository.AddressRepo;
import com.ait.app.repository.RestaurantAddressRepository;
import com.ait.app.requestbody.DeliveryFeeRequestDto;
import com.ait.app.requestbody.DeliveryFeeResponseDto;
import com.ait.app.service.DeliveryFeeService;

@Service
public class DeliveryFeeServiceImpl implements DeliveryFeeService{
	
    @Autowired
    AddressRepo addressRepo;
    
	@Autowired
	RestaurantAddressRepository restaurantAddressRepository;
	

	@Override
	public DeliveryFeeResponseDto calculateFee(DeliveryFeeRequestDto dto) {
		// TODO Auto-generated method stub
		
        Optional<RestaurantAddress> o = restaurantAddressRepository.findById(dto.getRestaurantAddressId());
        if (!o.isPresent()) {
            throw new PriceCustomException("Restaurant address details not found", HttpStatus.BAD_REQUEST);
        }
        RestaurantAddress restaurantAddress = o.get();
        
        
        Optional<Address> uo = addressRepo.findById(dto.getAddressId());
        if (!uo.isPresent()) {
            throw new PriceCustomException("Delivery address not found", HttpStatus.BAD_REQUEST);
        }
        Address userAddress = uo.get();
        
        double distance = 1.0;
        int idDiff = Math.abs(userAddress.getId() - restaurantAddress.getId()); 
        distance = 2.0 + (idDiff % 7);
        
        if (distance > 20.0) {
            throw new PriceCustomException("Delivery location is outside the 20km radius", HttpStatus.BAD_REQUEST);
        }
        
        double baseFee = 35.0;      
        double perKmRate = 8.0;     
        double finalFee = baseFee + (distance * perKmRate);
        
        DeliveryFeeResponseDto rdto = new DeliveryFeeResponseDto();
        rdto.setDistanceKm(distance);
        rdto.setFinalFee(finalFee);
		
		return rdto;
	}

}
