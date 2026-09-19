package com.ait.app.service;
import com.ait.app.requestbody.PriceCalculationRequestDto;
import com.ait.app.requestbody.PriceCalculationResponseDto;
import com.ait.app.requestbody.PriceResponseDto;

public interface PriceService {

	PriceCalculationResponseDto calculatePrice(PriceCalculationRequestDto dto);
	
	PriceResponseDto getPrice(int itemId);

}
