package com.ait.app.service;

import com.ait.app.requestbody.DeliveryFeeRequestDto;
import com.ait.app.requestbody.DeliveryFeeResponseDto;

public interface DeliveryFeeService {
	
    DeliveryFeeResponseDto calculateFee(DeliveryFeeRequestDto dto);

}
