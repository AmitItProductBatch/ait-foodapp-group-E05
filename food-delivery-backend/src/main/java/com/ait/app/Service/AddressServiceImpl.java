package com.ait.app.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.app.Dto.AddressDto;
import com.ait.app.model.Address;
import com.ait.app.repository.AddressRepo;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepo addressRepo;
	
	@Override
	public AddressDto createAddress(AddressDto addressDto) {
		
		Address address = new Address();
		address.setHouseNo(addressDto.getHouseNo());
		address.setStreetName(addressDto.getStreetName());
		address.setLandmark(addressDto.getLandmark());
		address.setCity(addressDto.getCity());
		address.setPinCode(addressDto.getPincode());
		
		Address saved = addressRepo.save(address);
		return toDto(saved);
	}
	

	@Override
	public List<AddressDto> getAllAddresses() {
		
		return addressRepo.findAll()
				.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public AddressDto getAddressById(int id) {
		Address address = addressRepo.findById(id).orElse(null);
		return toDto(address);
	}
	
	private AddressDto toDto(Address address) {
		if(address == null) {
			return null;
		}
		
		AddressDto dto = new AddressDto();
		dto.setId(address.getId());
		dto.setHouseNo(address.getHouseNo());
		dto.setStreetName(address.getStreetName());
		dto.setLandmark(address.getLandmark());
		dto.setCity(address.getCity());
		dto.setPincode(address.getPinCode());
		return dto;
	}

	
}
