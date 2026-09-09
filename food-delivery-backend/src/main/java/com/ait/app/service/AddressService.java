package com.ait.app.service;

import com.ait.app.requestbody.AddressRequestDto;

public interface AddressService {

	Long CreateAddress(Long userId, AddressRequestDto dto);

}