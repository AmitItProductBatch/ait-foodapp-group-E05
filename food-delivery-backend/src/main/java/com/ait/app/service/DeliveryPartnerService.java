package com.ait.app.service;

import java.util.List;

import com.ait.app.requestbody.DeliveryPartnerRequestDto;
import com.ait.app.requestbody.DeliveryPartnerResponseDto;

public interface DeliveryPartnerService {
	
	DeliveryPartnerResponseDto createDeliveryPartner(DeliveryPartnerRequestDto dto);
	DeliveryPartnerResponseDto getDeliveryPartner(Long id);
	List<DeliveryPartnerResponseDto> getAllDeliveryPartners();

}
