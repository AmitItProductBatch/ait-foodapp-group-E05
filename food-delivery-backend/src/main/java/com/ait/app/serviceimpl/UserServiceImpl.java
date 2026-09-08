package com.ait.app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.UserServiceCustomException;
import com.ait.app.model.User;
import com.ait.app.repository.UserRepository;
import com.ait.app.requestbody.UpdateProfileDto;
import com.ait.app.requestbody.UserRequestDto;

@Service
public class UserServiceImpl implements com.ait.app.Service.UserService {

    @Autowired
    private UserRepository ur;
    @Override
    public void registerUser(UserRequestDto dto) {

        if (ur.existsByEmail(dto.getEmail())) {
            throw new UserServiceCustomException(
                    "Email is already registered",
                    HttpStatus.CONFLICT
            );
        }

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new UserServiceCustomException(
                    "Name cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (dto.getAddress() == null || dto.getAddress().isBlank()) {
            throw new UserServiceCustomException(
                    "Address cannot be empty",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (dto.getPhNo() == null || dto.getPhNo().length() != 10) {
            throw new UserServiceCustomException(
                    "Phone number must be exactly 10 digits",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (dto.getPassword() == null || dto.getPassword().length() < 8) {
            throw new UserServiceCustomException(
                    "Password must be at least 8 characters long",
                    HttpStatus.BAD_REQUEST
            );
        }

        User u = new User();

        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setAddress(dto.getAddress());
        u.setPassword(dto.getPassword());
        u.setPhNo(dto.getPhNo());
        u.setRole("Customer");

        ur.save(u);
    }

    @Override
    public User updateProfile(Long id, UpdateProfileDto dto) {

        User user = ur.findById(id)
                .orElseThrow(() -> new UserServiceCustomException(
                        "User not found",
                        HttpStatus.NOT_FOUND
                ));
        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }
        if (dto.getPhNo() != null && !dto.getPhNo().isBlank()) {

            if (dto.getPhNo().length() != 10) {
                throw new UserServiceCustomException(
                        "Phone number must be exactly 10 digits",
                        HttpStatus.BAD_REQUEST
                );
            }

            user.setPhNo(dto.getPhNo());
        }
        if (dto.getAddress() != null && !dto.getAddress().isBlank()) {
            user.setAddress(dto.getAddress());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {

            if (!dto.getEmail().equalsIgnoreCase(user.getEmail())) {

            	if (ur.existsByEmail(dto.getEmail())) {
                    throw new UserServiceCustomException(
                            "Email is already registered",
                            HttpStatus.CONFLICT
                    );
                }

                user.setEmail(dto.getEmail());

                user.setEmailVerified(false);
            }
        }

        return ur.save(user);
    }
}