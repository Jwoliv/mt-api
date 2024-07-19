package com.mt.service;

import com.mt.dto.UserDto;
import com.mt.dto.security.LoginDto;
import com.mt.dto.security.SignUpDto;

public interface AuthServiceI {
    UserDto login(LoginDto login);
    UserDto singUp(SignUpDto signUp);
}
