package com.ait.app.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.UserServiceCustomException;
import com.ait.app.model.Address;
import com.ait.app.model.User;
import com.ait.app.repository.AddressRepository;
import com.ait.app.repository.UserRepository;
import com.ait.app.requestbody.AddressRequestDto;
import com.ait.app.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	UserRepository ur;
	
	@Autowired
	AddressRepository ar;
	
	@Override
	public Long CreateAddress(Long userId, AddressRequestDto dto) {
		// TODO Auto-generated method stub
		
        if (dto.getStreet() == null || dto.getStreet().equals("") ||
            dto.getCity() == null || dto.getCity().equals("") ||
            dto.getPincode() == null || dto.getPincode().equals("") ||
            dto.getAddressLabel() == null || dto.getAddressLabel().equals("")) {
            throw new UserServiceCustomException("Missing mandatory fields (streetAddress, city, postalCode, addressLabel)", HttpStatus.BAD_REQUEST);
        }
        
        Optional<User> o = ur.findById(userId);
        
        if (!o.isPresent()) {
            throw new UserServiceCustomException("User not found with ID: " + userId, HttpStatus.NOT_FOUND);
        }
        User u = o.get();
        
       
		
		Address address = new Address();
		address.setAddressLabel(dto.getAddressLabel());
        address.setStreet(dto.getStreet());
        address.setApartment(dto.getApartment());
        address.setLandmark(dto.getLandmark());
        address.setCity(dto.getCity());
        address.setPincode(dto.getPincode());
        address.setDeliveryInstructions(dto.getDeliveryInstructions());
        address.setUser(u);
        
        Address savedAddress = ar.save(address);
		return savedAddress.getId();
	}

}