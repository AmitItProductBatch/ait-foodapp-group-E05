package com.ait.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.UserServiceCustomException;
import com.ait.app.model.Role;
import com.ait.app.model.User;
import com.ait.app.repository.RoleRepo;
import com.ait.app.repository.UserRepository;

import com.ait.app.requestbody.UserDTO;

import com.ait.app.requestbody.UserRequestDto;
import com.ait.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository ur;
	
	@Autowired
	RoleRepo rr;

	@Override
	public UserDTO registerUser(UserRequestDto dto) {

		if (ur.existsByEmail(dto.getEmail())) {
			throw new UserServiceCustomException("Email is already registered", 
					HttpStatus.CONFLICT);
		}

		if (dto.getName() == null || dto.getName().isEmpty()) {
			throw new UserServiceCustomException("Name cannot be empty", 
					HttpStatus.BAD_REQUEST);
		}

		if (dto.getPhNo() == null || dto.getPhNo().length() != 10) {
			throw new UserServiceCustomException("Phone number must be exactly 10 digits",
					HttpStatus.BAD_REQUEST);
		}
		if (dto.getPassword() == null || dto.getPassword().length() < 8) {
			throw new UserServiceCustomException("Password must be at least 8 characters long",
					HttpStatus.BAD_REQUEST);
		}

		User u = new User();
		u.setName(dto.getName());
		u.setEmail(dto.getEmail());
		u.setPassword(dto.getPassword());
		u.setPhNo(dto.getPhNo());
		
		Optional<Role> o = rr.findById(dto.getRoleId());
		
		if(!o.isPresent()) {
			
			throw new UserServiceCustomException("Role Not Found", HttpStatus.NOT_FOUND);	
		}
		Role r =o.get();
		
		List<Role> rl = new ArrayList<>();
		rl.add(r);
		u.setRoles(rl);

		ur.save(u);
		User saved = ur.save(u);

		UserDTO response = new UserDTO();
		response.setId(saved.getId());
		response.setName(saved.getName());
		response.setEmail(saved.getEmail());
		response.setPhNo(saved.getPhNo());
		return response;

	}

	@Override
	public UserDTO getUserById(Long id) {
		Optional<User> optionalUser = ur.findById(id);

		if (optionalUser.isEmpty()) {
			throw new UserServiceCustomException("User not found", HttpStatus.NOT_FOUND);
		}

		User u = optionalUser.get();

		UserDTO dto = new UserDTO();

		dto.setId(u.getId());
		dto.setName(u.getName());
		dto.setEmail(u.getEmail());
		dto.setPhNo(u.getPhNo());

		return dto;

	}

	@Override
	public UserDTO updateProfile(Long id, UserDTO dto) {
		User u = ur.findById(id)
				.orElseThrow(() -> new UserServiceCustomException("User not found", HttpStatus.NOT_FOUND));

		if (dto.getName() != null) {
			u.setName(dto.getName());
		}
		if (dto.getPhNo() != null) {
			u.setPhNo(dto.getPhNo());
		}
		

		ur.save(u);
		return getUserById(id);
	}

	@Override
	public void deleteUser(Long id) {
		if (!ur.existsById(id)) {
			throw new UserServiceCustomException("User not found", HttpStatus.NOT_FOUND);
		}
		ur.deleteById(id);
	}

}
