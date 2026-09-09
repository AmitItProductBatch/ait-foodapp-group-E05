package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ait.app.Service.UserService;
import com.ait.app.model.User;
import com.ait.app.requestbody.UpdateProfileDto;
import com.ait.app.requestbody.UserDTO;
import com.ait.app.requestbody.UserRequestDto;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register User
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody UserRequestDto dto) {

        userService.registerUser(dto);

        return new ResponseEntity<>(
                "User registered successfully",
                HttpStatus.CREATED
        );
    }

    // Update User Profile
    @PutMapping("/{id}")
    public ResponseEntity<User> updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileDto dto) {

        User updatedUser = userService.updateProfile(id, dto);

        return ResponseEntity.ok(updatedUser);
    }

    // Get User By ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable Long id) {

        UserDTO dto = userService.getUserById(id);

        return ResponseEntity.ok(dto);
    }
}