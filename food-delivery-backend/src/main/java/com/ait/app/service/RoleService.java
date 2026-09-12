package com.ait.app.service;

import java.util.List;

import com.ait.app.model.Role;
import com.ait.app.requestbody.RoleRequestDto;
import com.ait.app.requestbody.RoleResponseDto;

public interface RoleService {

	RoleResponseDto addRole(RoleRequestDto dto);
	void assignRoleToUser(Long userId, Long roleId);
	RoleResponseDto getRoleById(Long id);
	List<Role> getAllRoles();
	String deleteRole(Long id);
	Role roleUpdate(Long id, Role r); 
   
	
	

}
