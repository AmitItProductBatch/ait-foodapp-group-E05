package com.ait.app.serviceimpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        
        String url = "https://project-osrm.org"
                + restaurantAddress.getLongitude() + "," + restaurantAddress.getLatitude() + ";"
                + userAddress.getLongitude() + "," + userAddress.getLatitude()
                + "?overview=false";


     RestTemplate restTemplate = new RestTemplate();
     ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);

     Map responseBody = responseEntity.getBody();

     double distance = 1.0;

     if (responseBody != null && responseBody.containsKey("routes")) {
         List routesList = (List) responseBody.get("routes");
         
         if (routesList != null && !routesList.isEmpty()) {
               
             Map firstRoute = (Map) routesList.get(0);
             
             double distanceMeters = Double.parseDouble(firstRoute.get("distance").toString());
             distance = distanceMeters / 1000.0;
         }
     } 
     else {
         throw new PriceCustomException("Failed to calculate driving route distance from map API", HttpStatus.BAD_REQUEST);
     }
        
        if (distance > 20.0) {
            throw new PriceCustomException("Delivery location is outside the 20km radius", HttpStatus.BAD_REQUEST);
        }
        
        double baseFee = 35.0;      
        double perKmRate = 8.0;     
        double finalFee = baseFee + (distance * perKmRate);
        
        distance = Math.round(distance * 100.0) / 100.0;
        finalFee = Math.round(finalFee * 100.0) / 100.0;
        
        DeliveryFeeResponseDto rdto = new DeliveryFeeResponseDto();
        rdto.setDistanceKm(distance);
        rdto.setFinalFee(finalFee);
		
		return rdto;
	}

}
