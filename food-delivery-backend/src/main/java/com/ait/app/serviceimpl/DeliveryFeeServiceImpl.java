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
import com.ait.app.model.Cart;
import com.ait.app.model.DeliveryFeeRule;
import com.ait.app.model.RestaurantAddress;
import com.ait.app.repository.AddressRepo;
import com.ait.app.repository.CartRepository;
import com.ait.app.repository.DeliveryFeeRuleRepo;
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
	
    @Autowired
    CartRepository cr;
    
    @Autowired
    DeliveryFeeRuleRepo deliveryFeeRuleRepo;
	

    @Override
    public DeliveryFeeResponseDto calculateFee(DeliveryFeeRequestDto dto) {

        Optional<RestaurantAddress> o =
                restaurantAddressRepository.findById(dto.getRestaurantAddressId());

        if (!o.isPresent()) {
            throw new PriceCustomException(
                    "Restaurant address details not found",
                    HttpStatus.BAD_REQUEST);
        }

        RestaurantAddress restaurantAddress = o.get();

        Optional<Address> uo =
                addressRepo.findById(dto.getAddressId());

        if (!uo.isPresent()) {
            throw new PriceCustomException(
                    "Delivery address not found",
                    HttpStatus.BAD_REQUEST);
        }

        Address userAddress = uo.get();

        Optional<DeliveryFeeRule> ruleOptional =
                deliveryFeeRuleRepo.findFirstByActiveTrueOrderByIdDesc();

        if (!ruleOptional.isPresent()) {
            throw new PriceCustomException(
                    "Delivery fee rules are not configured",
                    HttpStatus.BAD_REQUEST);
        }

        DeliveryFeeRule rule = ruleOptional.get();

        String url = "https://router.project-osrm.org/route/v1/driving/"
                + restaurantAddress.getLongitude() + ","
                + restaurantAddress.getLatitude() + ";"
                + userAddress.getLongitude() + ","
                + userAddress.getLatitude()
                + "?overview=false";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> responseEntity =
                restTemplate.getForEntity(url, Map.class);

        Map responseBody = responseEntity.getBody();

        double distance = 1.0;

        if (responseBody != null && responseBody.containsKey("routes")) {

            List routesList = (List) responseBody.get("routes");

            if (routesList != null && !routesList.isEmpty()) {

                Map firstRoute = (Map) routesList.get(0);

                double distanceMeters =
                        Double.parseDouble(firstRoute.get("distance").toString());

                distance = distanceMeters / 1000.0;
            }

        } else {

            throw new PriceCustomException(
                    "Failed to calculate driving route distance from map API",
                    HttpStatus.BAD_REQUEST);
        }

        if (distance > rule.getMaxDeliveryRadius()) {

            throw new PriceCustomException(
                    "Delivery location is outside the "
                            + rule.getMaxDeliveryRadius() + "km radius",
                    HttpStatus.BAD_REQUEST);
        }

        double baseFee = rule.getBaseFee();

        double perKmRate = rule.getPerKmRate();

        double finalFee = baseFee + (distance * perKmRate);

        double freeDeliveryDistanceThreshold = 3.0;

        if (distance <= freeDeliveryDistanceThreshold) {
            finalFee = 0.0;
        }

        if (userAddress != null) {

            Cart c = cr.findByUserId((long) userAddress.getId());

            if (c != null
                    && c.getTotalAmount() >= rule.getFreeDeliveryThreshold()) {

                finalFee = 0.0;
            }
        }

        distance = Math.round(distance * 100.0) / 100.0;

        finalFee = Math.round(finalFee * 100.0) / 100.0;

        DeliveryFeeResponseDto rdto = new DeliveryFeeResponseDto();

        rdto.setDistanceKm(distance);
        rdto.setFinalFee(finalFee);

        return rdto;
    }

    @Override
    public DeliveryFeeRule getActiveDeliveryFeeRule() {

        Optional<DeliveryFeeRule> ruleOptional =
                deliveryFeeRuleRepo.findFirstByActiveTrueOrderByIdDesc();

        if (!ruleOptional.isPresent()) {

            throw new PriceCustomException(
                    "Delivery fee rules are not configured",
                    HttpStatus.BAD_REQUEST);
        }

        return ruleOptional.get();
    }
}