package com.ait.app.Service;

import com.ait.app.model.User;
import com.ait.app.requestbody.UpdateProfileDto;
import com.ait.app.requestbody.UserDTO;
import com.ait.app.requestbody.UserRequestDto;

public interface UserService {

    // Register a new user
    void registerUser(UserRequestDto dto);

    // Update existing user profile
    User updateProfile(Long id, UpdateProfileDto dto);

    // Get user details by ID
    UserDTO getUserById(Long id);
}