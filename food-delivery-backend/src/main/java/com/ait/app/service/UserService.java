package com.ait.app.service;


import com.ait.app.requestbody.UserDTO;


import com.ait.app.requestbody.UserRequestDto;

public interface UserService {
	
	void RegisterUser(UserRequestDto dto);

    UserDTO getUserById(Long id);
    void deleteUser(Long id);
    
}




