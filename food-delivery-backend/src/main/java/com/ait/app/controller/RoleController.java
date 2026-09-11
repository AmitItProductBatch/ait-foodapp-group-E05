package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.RoleRequestDto;
import com.ait.app.requestbody.RoleResponseDto;
import com.ait.app.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	RoleService rs;

	@PostMapping("/add")
	public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleRequestDto dto){
		RoleResponseDto rdto = rs.addRole(dto);
		return new ResponseEntity(rdto, HttpStatus.CREATED);
	}

}
