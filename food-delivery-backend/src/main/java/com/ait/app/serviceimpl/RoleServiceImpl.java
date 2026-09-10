package com.ait.app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.UserServiceCustomException;
import com.ait.app.model.Role;
import com.ait.app.repository.RoleRepo;
import com.ait.app.requestbody.RoleRequestDto;
import com.ait.app.requestbody.RoleResponseDto;
import com.ait.app.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepo rr;
	
	@Override
	public RoleResponseDto addRole(RoleRequestDto dto) {
		// TODO Auto-generated method stub
		
		if (dto.getName() == null || dto.getName().isEmpty() ||dto.getDescription() == null || dto.getDescription().isEmpty()) {
	            throw new UserServiceCustomException("Missing mandatory fields", HttpStatus.BAD_REQUEST);
	        }
		
		if(rr.existsByName(dto.getName())) {
			throw new UserServiceCustomException("Role is already exists", HttpStatus.CONFLICT);
		}
		
		if (!dto.getName().startsWith("Role_")) {
            throw new UserServiceCustomException("Role name must start with Role_", HttpStatus.BAD_REQUEST);
        }
		
		Role r = new Role();
		r.setName(dto.getName());
		r.setDiscription(dto.getDescription());
		
		Role savedRole = rr.save(r);
		
		RoleResponseDto responseDto = new RoleResponseDto();
		responseDto.setName(savedRole.getName());
		responseDto.setDescription(savedRole.getDiscription());
		
		return responseDto;
	}

}
