package com.ait.app.service;

import java.util.List;

import com.ait.app.requestbody.DeliveryPartnerRequestDto;
import com.ait.app.requestbody.DeliveryPartnerResponseDto;
import com.ait.app.requestbody.DeliveryPartnerUpdateRequestDto;

public interface DeliveryPartnerService {

	DeliveryPartnerResponseDto createDeliveryPartner(DeliveryPartnerRequestDto dto);


	DeliveryPartnerResponseDto getDeliveryPartner(Long id);

	List<DeliveryPartnerResponseDto> getAllDeliveryPartners();

	DeliveryPartnerResponseDto updateDeliveryPartner(Long id, DeliveryPartnerUpdateRequestDto dto);


	void deleteDeliveryPartner(Long id);
}
