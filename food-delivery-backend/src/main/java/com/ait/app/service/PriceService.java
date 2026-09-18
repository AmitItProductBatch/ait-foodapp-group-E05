package com.ait.app.service;
import com.ait.app.requestbody.PriceCalculationRequestDto;
import com.ait.app.requestbody.PriceCalculationResponseDto;

public interface PriceService {

	PriceCalculationResponseDto calculatePrice(PriceCalculationRequestDto dto);

}
