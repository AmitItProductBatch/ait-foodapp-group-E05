package com.ait.app.service;

import com.ait.app.requestbody.RoleRequestDto;
import com.ait.app.requestbody.RoleResponseDto;

public interface RoleService {

	RoleResponseDto addRole(RoleRequestDto dto);
	
	

}
