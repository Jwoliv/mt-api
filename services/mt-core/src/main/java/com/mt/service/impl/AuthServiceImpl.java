package com.mt.service.impl;

import com.mt.dto.model_dto.UserDto;
import com.mt.dto.security.LoginDto;
import com.mt.dto.security.SignUpDto;
import com.mt.security.UserAuthenticationProvider;
import lombok.Setter;
import com.mt.mapper.UserMapper;
import com.mt.repository.UserRepository;
import com.mt.service.AuthService;
import com.mt.service.UserService;
import com.mt.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Setter(onMethod = @__({@Autowired}))
    private UserAuthenticationProvider provider;
    @Setter(onMethod = @__({@Autowired}))
    private UserService userService;
    @Setter(onMethod = @__({@Autowired}))
    private UserRepository userRepository;
    @Setter(onMethod = @__({@Autowired}))
    private UserMapper userMapper;
    @Setter(onMethod = @__({@Autowired}))
    private AuthUtils authUtils;


    @Override
    public UserDto login(LoginDto login) {
        var user = userRepository.findByUsername(login.getUsername()).orElse(null);
        if (authUtils.isValidLoginPassword(login.getPassword(), user.getPassword())) {
            var checkedUser = userMapper.mapToUserDto(user);
            provider.setTokenToUser(checkedUser);
            return checkedUser;
        }
        return null;
    }

    @Override
    public UserDto singUp(SignUpDto signUp) {
        var newUser = userService.signUp(authUtils.buildNewUser(signUp));
        provider.setTokenToUser(newUser);
        return newUser;
    }

}