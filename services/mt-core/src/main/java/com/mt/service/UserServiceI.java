package com.mt.service;


import com.mt.dto.model_dto.UserDto;
import com.mt.model.User;

public interface UserServiceI {
    UserDto findByEmail(String email);
    UserDto findByUsername(String username);
    UserDto signUp(User user);
}
