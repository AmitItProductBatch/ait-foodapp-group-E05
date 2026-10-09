package com.ait.app.service;

import com.ait.app.requestbody.DeliveryPartnerRequestDto;
import com.ait.app.requestbody.DeliveryPartnerResponseDto;

public interface DeliveryPartnerService {
	
	DeliveryPartnerResponseDto createDeliveryPartner(DeliveryPartnerRequestDto dto);

}
