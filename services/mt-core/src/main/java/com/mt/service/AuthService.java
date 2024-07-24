package com.mt.service;

import com.mt.dto.model_dto.UserDto;
import com.mt.dto.security.LoginDto;
import com.mt.dto.security.SignUpDto;

public interface AuthService {
    UserDto login(LoginDto login);
    UserDto singUp(SignUpDto signUp);
}
