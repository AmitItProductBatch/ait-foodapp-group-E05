package com.ait.app.service;

import java.util.List;

import com.ait.app.requestbody.AddressDto;


public interface AddressService {
	
	AddressDto createAddress(AddressDto addressDto);
	
	List<AddressDto> getAllAddresses();
	
	AddressDto getAddressById(int id);

}
