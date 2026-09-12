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
import com.ait.app.requestbody.RoleRequestDto;
import com.ait.app.requestbody.RoleResponseDto;
import com.ait.app.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepo rr;
	
	@Autowired
	UserRepository ur;
	
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

	@Override
	public void assignRoleToUser(Long userId, Long roleId) {
		// TODO Auto-generated method stub
		
		Optional<User> o = ur.findById(userId);
		if(!o.isPresent()) {
			throw new UserServiceCustomException("User not found", HttpStatus.NOT_FOUND);
		}
		 
		User exUser = o.get();
		
		Optional<Role> ou = rr.findById(roleId);
		
		if(!ou.isPresent()) {
			throw new UserServiceCustomException("Not found", HttpStatus.NOT_FOUND);
		}
		
		Role userRole = ou.get();
		
		
		
		List<Role> userRoles = exUser.getRoles();
		if(userRoles==null) {
			userRoles = new ArrayList<>();
			
		}
		
		userRoles.add(userRole);
		exUser.setRoles(userRoles);
		
		ur.save(exUser);
		
	}

	@Override
	public RoleResponseDto getRoleById(Long id) {
		Optional<Role> o = rr.findById(id);
		
		if(!o.isPresent()) {
			throw new UserServiceCustomException("Not Present", HttpStatus.NOT_FOUND);
		}
		Role r = o.get();
		
		RoleResponseDto resDto = new RoleResponseDto();
        resDto.setName(r.getName());
        resDto.setDescription(r.getDiscription());
        return resDto;
	}

	@Override
	public String deleteRole(Long id) {
		// TODO Auto-generated method stub
		Optional<Role> o = rr.findById(id);
		if(!o.isPresent()) {
            throw new UserServiceCustomException("Role not found", HttpStatus.NOT_FOUND);
        }
        rr.deleteById(id);
        return "Role deleted successfully";
	}

	@Override
	public List<Role> getAllRoles() {
		return rr.findAll();
	}

	@Override
	public Role roleUpdate(Long id, Role r) {
	        Role exRole = rr.findById(id).get();
	        if (r.getName() != null) { 
	            exRole.setName(r.getName()); 
	        }
	        return rr.save(exRole);
	    }
	
	
	

}
