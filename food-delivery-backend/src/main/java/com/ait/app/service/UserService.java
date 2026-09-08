package com.ait.app.Service;

import com.ait.app.model.User;
import com.ait.app.requestbody.UpdateProfileDto;
import com.ait.app.requestbody.UserRequestDto;

public interface UserService {

    void registerUser(UserRequestDto dto);

    User updateProfile(Long id, UpdateProfileDto dto);
}