
package com.ait.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.service.AddressService;
import com.ait.app.exception.UserServiceCustomException;
import com.ait.app.model.Address;
import com.ait.app.repository.AddressRepo;
import com.ait.app.requestbody.AddressDto;

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

        List<Address> addresses = addressRepo.findAll();

        List<AddressDto> addressDtoList = new ArrayList<AddressDto>();

        for (Address address : addresses) {
            AddressDto dto = toDto(address);
            addressDtoList.add(dto);
        }

        return addressDtoList;
    }

    @Override
    public AddressDto getAddressById(int id) {

        Address address = addressRepo.findById(id).orElse(null);
        if (address == null) {
			throw new UserServiceCustomException("Address not found", HttpStatus.NOT_FOUND);
		}

        return toDto(address);
    }

    private AddressDto toDto(Address address) {

        if (address == null) {
            return null;
        }

        AddressDto dto = new AddressDto();

        dto.setId(address.getId());
        dto.setHouseNo(address.getHouseNo());
        dto.setStreetName(address.getStreetName());
        dto.setLandmark(address.getLandmark());
        dto.setCity(address.getCity());
        dto.setPinCode(address.getPinCode());

        return dto;
    }
}

