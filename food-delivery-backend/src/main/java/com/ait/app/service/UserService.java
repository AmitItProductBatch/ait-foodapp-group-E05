package com.ait.app.service;

import com.ait.app.requestbody.UserRequestDto;
import com.ait.app.requestbody.UserResponseDTO;
import com.ait.app.requestbody.UserUpdateDto;

public interface UserService {

	void registerUser(UserRequestDto dto);

	UserResponseDTO getUserById(Long id);

	UserResponseDTO updateUserProfile(Long id, UserUpdateDto updateDto);

	void deleteUser(Long id);


}
