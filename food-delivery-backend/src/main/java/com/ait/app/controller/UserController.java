package com.ait.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.UserRequestDto;
import com.ait.app.requestbody.UserResponseDTO;
import com.ait.app.requestbody.UserUpdateDto;
import com.ait.app.service.UserService;

import com.ait.app.requestbody.UserRequestDto;
import com.ait.app.service.UserService;

@RestController
@RequestMapping({ "/users" })

public class UserController {

	@Autowired
	UserService us;

	@PostMapping("/register")
	public ResponseEntity registerUser(@RequestBody UserRequestDto dto) {
		 us.registerUser(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {

		UserResponseDTO dto = us.getUserById(id);

		return ResponseEntity.ok(dto);
	}

	@PutMapping("/{id}")
    public ResponseEntity updateProfile(@PathVariable Long id, @RequestBody UserUpdateDto updateDto) {
        UserResponseDTO updatedProfile = us.updateUserProfile(id, updateDto);
        return new ResponseEntity<>(updatedProfile , HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		us.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

}
