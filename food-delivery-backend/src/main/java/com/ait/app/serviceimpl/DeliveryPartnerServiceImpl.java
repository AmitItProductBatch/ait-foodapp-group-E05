package com.ait.app.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.DeliveryPartnerCustomException;
import com.ait.app.model.DeliveryPartner;
import com.ait.app.repository.DeliveryPartnerRepository;
import com.ait.app.requestbody.DeliveryPartnerRequestDto;
import com.ait.app.requestbody.DeliveryPartnerResponseDto;
import com.ait.app.service.DeliveryPartnerService;

@Service
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService{
	
	@Autowired
	DeliveryPartnerRepository dpr;

	@Override
	public DeliveryPartnerResponseDto createDeliveryPartner(DeliveryPartnerRequestDto dto) {
		// TODO Auto-generated method stub
		
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new DeliveryPartnerCustomException("Delivery Partner Name is mandatory", HttpStatus.BAD_REQUEST);
        }
        
        if (dto.getPhNo() == null || dto.getPhNo().isEmpty()) {
            throw new DeliveryPartnerCustomException("Phone number must be provided", HttpStatus.BAD_REQUEST);
        }
        
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new DeliveryPartnerCustomException("Email address cannot be blank", HttpStatus.BAD_REQUEST);
        }
        
        if (!dto.getPhNo().matches("^[6-9]\\d{9}$")) {
            throw new DeliveryPartnerCustomException("Validation Failed: Phone number must be a valid 10-digit Indian mobile number", HttpStatus.BAD_REQUEST);
        }
        
        if (!dto.getEmail().toLowerCase().endsWith("@gmail.com")) {
            throw new DeliveryPartnerCustomException("Email must end with @gmail.com", HttpStatus.BAD_REQUEST);
        }
        
        if (dto.getVehicleNumber() == null || dto.getVehicleNumber().isEmpty()) {
            throw new DeliveryPartnerCustomException("Vehicle number is required", HttpStatus.BAD_REQUEST);
        }
        
        DeliveryPartner dp =  new DeliveryPartner();
        dp.setName(dto.getName());
        dp.setEmail(dto.getEmail());
        dp.setPhNo(dto.getPhNo());
        dp.setVehicleType(dto.getVehicleType());
        dp.setVehicleNumber(dto.getVehicleNumber());
        dp.setAvailable(dto.isAvailable());
        dp.setCreatedAt(LocalDateTime.now());
        dp.setUpdatedAt(LocalDateTime.now());
        DeliveryPartner savedDp = dpr.save(dp);
        
        DeliveryPartnerResponseDto rDto = new DeliveryPartnerResponseDto();
        rDto.setId(savedDp.getId());
        rDto.setName(savedDp.getName());
        rDto.setAvailable(savedDp.isAvailable());
		return rDto; 
	}

	@Override
	public DeliveryPartnerResponseDto getDeliveryPartner(Long id) {
		 Optional<DeliveryPartner> optional = dpr.findById(id);

		    if (!optional.isPresent()) {
		        throw new DeliveryPartnerCustomException(
		                "Delivery partner not found with ID: " + id,
		                HttpStatus.NOT_FOUND);
		    }

		    DeliveryPartner dp = optional.get();

		    DeliveryPartnerResponseDto response = new DeliveryPartnerResponseDto();

		    response.setId(dp.getId());
		    response.setName(dp.getName());
		    response.setAvailable(dp.isAvailable());

		    return response;
	}

	@Override
	public List<DeliveryPartnerResponseDto> getAllDeliveryPartners() {
		List<DeliveryPartner> partners = dpr.findAll();

		List<DeliveryPartnerResponseDto> responseList = new ArrayList();

		for (DeliveryPartner dp : partners) {

			DeliveryPartnerResponseDto response = new DeliveryPartnerResponseDto();

			response.setId(dp.getId());
			response.setName(dp.getName());
			response.setAvailable(dp.isAvailable());

			responseList.add(response);
		}

		return responseList;
	}

}
