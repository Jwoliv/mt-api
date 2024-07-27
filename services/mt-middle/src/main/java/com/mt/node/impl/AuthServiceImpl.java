package com.mt.node.impl;

import com.mt.dto.model_dto.UserDto;
import com.mt.dto.security.LoginDto;
import com.mt.dto.security.SignUpDto;
import com.mt.feign.AuthCore;
import com.mt.node.AuthService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Setter(onMethod = @__({@Autowired}))
    private AuthCore authCore;

    @Override
    public UserDto login(LoginDto login) {
        return authCore.login(login);
    }

    @Override
    public UserDto singUp(SignUpDto signUp) {
        return authCore.signUp(signUp);
    }
}
