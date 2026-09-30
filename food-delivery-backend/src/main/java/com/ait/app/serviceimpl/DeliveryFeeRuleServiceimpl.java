package com.ait.app.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.DeliveryFeeException;
import com.ait.app.model.DeliveryFeeRule;
import com.ait.app.repository.DeliveryFeeRuleRepo;
import com.ait.app.requestbody.DeliveryFeeRuleRequestDto;
import com.ait.app.requestbody.DeliveryFeeRuleResponseDto;
import com.ait.app.service.DeliveryFeeRuleService;

@Service
public class DeliveryFeeRuleServiceimpl implements DeliveryFeeRuleService{
	
	@Autowired
	private DeliveryFeeRuleRepo deliveryFeeRuleRepo;

	@Override
	public DeliveryFeeRuleResponseDto updateDeliveryFeeRule(DeliveryFeeRuleRequestDto dto) {
		
		if(dto.getBaseFee() == null || dto.getBaseFee() <=0) {
			throw new DeliveryFeeException(
					"Base fee must be greater than zero",
					HttpStatus.BAD_REQUEST);
			
		}
		
		if(dto.getPerKmRate() == null || dto.getPerKmRate() <=0) {
			throw new DeliveryFeeException(
					"Per km rate must be greater than zero",
					HttpStatus.BAD_GATEWAY);
		}
		
		 if (dto.getMaxDeliveryRadius() == null || dto.getMaxDeliveryRadius() <= 0) {
	            throw new DeliveryFeeException(
	                    "Maximum delivery radius must be greater than zero",
	                    HttpStatus.BAD_REQUEST);
	        }

	        if (dto.getFreeDeliveryThreshold() == null || dto.getFreeDeliveryThreshold() <= 0) {
	            throw new DeliveryFeeException(
	                    "Free delivery threshold must be greater than zero",
	                    HttpStatus.BAD_REQUEST);
	        }

	        Optional<DeliveryFeeRule> optionalRule =
	                deliveryFeeRuleRepo.findFirstByActiveTrueOrderByIdDesc();

	        DeliveryFeeRule rule;

	        if (optionalRule.isPresent()) {
	            rule = optionalRule.get();
	        } else {
	            rule = new DeliveryFeeRule();
	        }

	        rule.setBaseFee(dto.getBaseFee());
	        rule.setPerKmRate(dto.getPerKmRate());
	        rule.setMaxDeliveryRadius(dto.getMaxDeliveryRadius());
	        rule.setFreeDeliveryThreshold(dto.getFreeDeliveryThreshold());
	        rule.setActive(true);
	        
	        DeliveryFeeRule savedRule = deliveryFeeRuleRepo.save(rule);

	        DeliveryFeeRuleResponseDto response = new DeliveryFeeRuleResponseDto();

	        response.setId(savedRule.getId());
	        response.setBaseFee(savedRule.getBaseFee());
	        response.setPerKmRate(savedRule.getPerKmRate());
	        response.setMaxDeliveryRadius(savedRule.getMaxDeliveryRadius());
	        response.setFreeDeliveryThreshold(savedRule.getFreeDeliveryThreshold());
	        response.setActive(savedRule.isActive());

	        return response;
	    } 
	}
	
	


