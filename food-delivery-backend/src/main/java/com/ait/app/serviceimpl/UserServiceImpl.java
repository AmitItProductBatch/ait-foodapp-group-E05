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


import com.ait.app.requestbody.UserRequestDto;
import com.ait.app.requestbody.UserResponseDTO;
import com.ait.app.requestbody.UserUpdateDto;
import com.ait.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository ur;
	
	@Autowired
	AddressRepository ar;

	@Override
	public void registerUser(UserRequestDto dto) {

		if (ur.existsByEmail(dto.getEmail())) {
			throw new UserServiceCustomException("Email is already registered", HttpStatus.CONFLICT);
		}

		if (dto.getName() == null || dto.getName().isEmpty()) {
			throw new UserServiceCustomException("Name cannot be empty", HttpStatus.BAD_REQUEST);
		}
		
		if (dto.getPhNo() == null || dto.getPhNo().length() != 10) {
			throw new UserServiceCustomException("Phone number must be exactly 10 digits", HttpStatus.BAD_REQUEST);
		}
		if (dto.getPassword() == null || dto.getPassword().length() < 8) {
			throw new UserServiceCustomException("Password must be at least 8 characters long", HttpStatus.BAD_REQUEST);
		}

		User u = new User();
		u.setName(dto.getName());
		u.setEmail(dto.getEmail());
		u.setPassword(dto.getPassword());
		u.setPhNo(dto.getPhNo());
		u.setRole("Customer");

		ur.save(u);

	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		Optional<User> optionalUser = ur.findById(id);

		if (optionalUser.isEmpty()) {
			throw new UserServiceCustomException("User not found", HttpStatus.NOT_FOUND);
		}

		User u = optionalUser.get();

		UserResponseDTO dto = new UserResponseDTO();

		dto.setId(u.getId());
		dto.setName(u.getName());
		dto.setEmail(u.getEmail());
		dto.setPhNo(u.getPhNo());
		dto.setRole(u.getRole());

		return dto;

	}
	
	@Override
	public UserResponseDTO updateUserProfile(Long id, UserUpdateDto updateDto) {
		if (!ur.existsById(id)) {
			throw new UserServiceCustomException("User not found with ID: " + id, HttpStatus.NOT_FOUND);
		}
		
		User user = ur.findById(id).get();
		
		if (updateDto.getName() != null && !updateDto.getName().equals("")) {
			user.setName(updateDto.getName());
		}

		if (updateDto.getPhNo() != null && !updateDto.getPhNo().equals("")) {
			if (updateDto.getPhNo().length() != 10) {
				throw new UserServiceCustomException("Phone number must be exactly 10 digits", HttpStatus.BAD_REQUEST);
			}
			user.setPhNo(updateDto.getPhNo());
		}

		if (updateDto.getAddress() != null && !updateDto.getAddress().equals("")) {
			Address address = new Address();
			address.setAddressLabel("Home");
			address.setStreet(updateDto.getAddress());
			address.setCity("Default City");
			address.setPincode("000000");
			address.setUser(user); 
			
			ar.save(address); 
		}
		
		User updatedUser = ur.save(user);
		
		UserResponseDTO responsedto = new UserResponseDTO();
		responsedto.setId(updatedUser.getId());
		responsedto.setName(updatedUser.getName());
		responsedto.setEmail(updatedUser.getEmail());
		responsedto.setPhNo(updatedUser.getPhNo());
		responsedto.setRole(updatedUser.getRole());
		
		return responsedto;
	}
	
		
	

				


	

	@Override
	public void deleteUser(Long id) {
		if (!ur.existsById(id)) {
			throw new UserServiceCustomException("User not found", HttpStatus.NOT_FOUND);
		}
		ur.deleteById(id);
	}

	

}
