package com.ait.app.service;

import java.util.List;

import com.ait.app.requestbody.UserDTO;
import com.ait.app.requestbody.UserRequestDto;

public interface UserService {

	UserDTO registerUser(UserRequestDto dto);

	UserDTO getUserById(Long id);
    List<UserDTO> getUserByName(String name);
	UserDTO updateProfile(Long id, UserDTO dto);

	void deleteUser(Long id);
}
