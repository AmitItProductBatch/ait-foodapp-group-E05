package com.ait.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.UserServiceCustomException;
import com.ait.app.model.Address;
import com.ait.app.model.User;
import com.ait.app.repository.AddressRepo;
import com.ait.app.repository.UserRepository;
import com.ait.app.requestbody.AddressDto;
import com.ait.app.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    UserRepository userRepository;

    @Override
    public AddressDto createAddress(AddressDto addressDto) {

        if (!userRepository.existsById(addressDto.getUserId())) {
            throw new UserServiceCustomException(
                    "User not found with id: " + addressDto.getUserId(),
                    HttpStatus.NOT_FOUND);
        }

        User user = userRepository.findById(addressDto.getUserId()).get();

        Address address = new Address();

        address.setHouseNo(addressDto.getHouseNo());
        address.setStreetName(addressDto.getStreetName());
        address.setLandmark(addressDto.getLandmark());
        address.setCity(addressDto.getCity());
        address.setPinCode(addressDto.getPincode());
        address.setUser(user);

        addressRepo.save(address);

        AddressDto dto = new AddressDto();

        dto.setId(address.getId());
        dto.setHouseNo(addressDto.getHouseNo());
        dto.setStreetName(addressDto.getStreetName());
        dto.setLandmark(addressDto.getLandmark());
        dto.setCity(addressDto.getCity());
        dto.setPincode(addressDto.getPincode());
        dto.setUserId(addressDto.getUserId());

        return dto;
    }

    @Override
    public List<AddressDto> getAllAddresses() {

        List<Address> addresses = addressRepo.findAll();

        List<AddressDto> dtoList = new ArrayList<>();

        for (Address address : addresses) {

            AddressDto dto = new AddressDto();

            dto.setId(address.getId());
            dto.setHouseNo(address.getHouseNo());
            dto.setStreetName(address.getStreetName());
            dto.setLandmark(address.getLandmark());
            dto.setCity(address.getCity());
            dto.setPincode(address.getPinCode());

            if (address.getUser() != null) {
                dto.setUserId(address.getUser().getId());
            }

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public AddressDto getAddressById(int id) {

        Address address = addressRepo.findById(id).orElse(null);

        if (address == null) {
            throw new UserServiceCustomException(
                    "Address not found",
                    HttpStatus.NOT_FOUND);
        }

        AddressDto dto = new AddressDto();

        dto.setId(address.getId());
        dto.setHouseNo(address.getHouseNo());
        dto.setStreetName(address.getStreetName());
        dto.setLandmark(address.getLandmark());
        dto.setCity(address.getCity());
        dto.setPincode(address.getPinCode());

        if (address.getUser() != null) {
            dto.setUserId(address.getUser().getId());
        }

        return dto;
    }
}