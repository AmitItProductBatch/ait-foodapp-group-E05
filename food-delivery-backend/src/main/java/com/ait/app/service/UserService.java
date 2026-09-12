package com.ait.app.service;

import com.ait.app.requestbody.UserDTO;
import com.ait.app.requestbody.UserRequestDto;

public interface UserService {

	UserDTO registerUser(UserRequestDto dto);

	UserDTO getUserById(Long id);

	UserDTO updateProfile(Long id, UserDTO dto);

	void deleteUser(Long id);
}
