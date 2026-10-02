package com.ait.app.service;

import com.ait.app.requestbody.DeliveryFeeRuleRequestDto;
import com.ait.app.requestbody.DeliveryFeeRuleResponseDto;

public interface DeliveryFeeRuleService {
	
	DeliveryFeeRuleResponseDto updateDeliveryFeeRule(DeliveryFeeRuleRequestDto dto);

}
