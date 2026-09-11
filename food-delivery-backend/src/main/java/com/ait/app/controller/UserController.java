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

import com.ait.app.requestbody.UserDTO;
import com.ait.app.requestbody.UserRequestDto;
import com.ait.app.service.UserService;

@RestController
@RequestMapping({ "/users" })
public class UserController {

	@Autowired
	UserService us;

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserRequestDto dto) {
		UserDTO created = us.registerUser(dto);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {

		UserDTO dto = us.getUserById(id);

		return ResponseEntity.ok(dto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateProfile(@PathVariable Long id,
			@RequestBody UserDTO dto) {
		return ResponseEntity.ok(us.updateProfile(id, dto));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser( @PathVariable Long id) { us.deleteUser(id);
	return new ResponseEntity<>( "User deleted successfully", HttpStatus.OK );
	}

}
